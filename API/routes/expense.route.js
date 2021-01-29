const express = require('express');
const creatError = require('http-errors');
const router = express.Router();

const Income = require('../models/expense.model');

/** posting a new Category */
router.post('/', async(req, res, next) => {
    try {
        const result = req.body;

        if (!result.name || !result.amount)
            throw creatError.BadRequest(`all data must be provided provided`);

        // creat & save new Category
        const newIncome= new Income(result);
        const saveIncome = await newIncome.save();
        res.send({ saveIncome })

    } catch (error) {
        next(error)
    }
});

/** getting all Categorys as an array */
router.get('/', async(req, res, next) => {
    try {
        // getting all data
        const allIncome = await Income.find().select('_id name amount');
        if (!allIncome)
            throw creatError.NotFound(`No income are found`)

        res.send({ allIncome });

    } catch (error) {
        next(error)
    }
});

/** updating a specific Category with :id */
router.put('/:id', async(req, res, next) => {
    try {
        const id = req.params.id;

        // rdandent value checking
        const existIncome = await Income.findOne({ _id: id });
        if (!existIncome)
            throw creatError.NotFound(`Income with id ${id} does not exists`);

        // updating
        const updatedIncome = await Income.findByIdAndUpdate(id, req.body);
        if (!updatedIncome)
            throw creatError.BadRequest(`Unable to update, try again`)

        res.send({ message: 'Incomeupdated successfully!' });


    } catch (error) {
        next(error)
    }
});

/** deleting a specific Category with :id */
router.delete('/:id', async(req, res, next) => {
    try {
        const id = req.params.id;

        // rdandent value checking
        const existIncome = await Income.findOne({ _id: id });
        if (!existIncome)
            throw creatError.NotFound(`Income with id ${id} does not exists`);

        // updating
        const deletedIncome = await Income.findByIdAndDelete(id);
        if (!deletedIncome)
            throw creatError.BadRequest(`Unable to delete, try again`)

        res.send({ message: 'Income deleted successfully!' });


    } catch (error) {
        next(error)
    }
});

/** exporting the route */
module.exports = router;