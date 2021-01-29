const express = require('express');
const creatError = require('http-errors');
const router = express.Router();

const Category = require('../models/category.model');

/** posting a new Category */
router.post('/', async(req, res, next) => {
    try {
        const result = req.body;

        if (!result.title)
            throw creatError.BadRequest(`title & description both must be provided`);

        // rdandent value checking
        const existCategory = await Category.findOne({ title: result.title });
        if (existCategory)
            throw creatError.Conflict(`${result.title} Category already exists`);

        // creat & save new Category
        const newCategory = new Category(result);
        const saveCategory = await newCategory.save();
        res.send({ saveCategory })

    } catch (error) {
        next(error)
    }
});

/** getting all Categorys as an array */
router.get('/', async(req, res, next) => {
    try {
        // getting all data
        const allCategories = await Category.find().select('_id title');
        if (!allCategories)
            throw creatError.NotFound(`No Categorys are found`)

        res.send({ allCategories });

    } catch (error) {
        next(error)
    }
});

/** updating a specific Category with :id */
router.put('/:id', async(req, res, next) => {
    try {
        const id = req.params.id;

        // rdandent value checking
        const existCategory = await Category.findOne({ _id: id });
        if (!existCategory)
            throw creatError.NotFound(`Category with id ${id} does not exists`);

        // updating
        const updatedCategory = await Category.findByIdAndUpdate(id, req.body);
        if (!updatedCategory)
            throw creatError.BadRequest(`Unable to update, try again`)

        res.send({ message: 'Category updated successfully!' });


    } catch (error) {
        next(error)
    }
});

/** deleting a specific Category with :id */
router.delete('/:id', async(req, res, next) => {
    try {
        const id = req.params.id;

        // rdandent value checking
        const existCategory = await Category.findOne({ _id: id });
        if (!existCategory)
            throw creatError.NotFound(`Category with id ${id} does not exists`);

        // updating
        const deletedCategory = await Category.findByIdAndDelete(id);
        if (!deletedCategory)
            throw creatError.BadRequest(`Unable to delete, try again`)

        res.send({ message: 'Category deleted successfully!' });


    } catch (error) {
        next(error)
    }
});

/** exporting the route */
module.exports = router;