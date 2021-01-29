const express = require('express');
const creatError = require('http-errors');
const router = express.Router();

const Gole = require('../models/goal.model');

/** posting a new Gole */
router.post('/', async(req, res, next) => {
    try {
        const result = req.body;

        if (!result.name || !result.amount || !result.date)
            throw creatError.BadRequest(`All field of Goal must be provided`);

        // rdandent value checking
        const existGole = await Gole.findOne({ name:result.name,amount: result.amount, currency:result.amount, date:result.date});
        if (existGole)
            throw creatError.Conflict(`${result.name} Gole already exists`);

        // creat & save new gole
        const newGole = new Gole(result);
        const saveGole = await newGole.save();
        res.send({ saveGole })

    } catch (error) {
        next(error)
    }
});

/** getting all Gole as an array */
router.get('/', async(req, res, next) => {
    try {
        // getting all data
        const allGoles = await Gole.find().select('_id name amount date user');
        if (!allGoles)
            throw creatError.NotFound(`No Gole found`)

        res.send({ allGoles });

    } catch (error) {
        next(error)
    }
});

/** updating a specific Gole with :id */
router.put('/:id', async(req, res, next) => {
    try {
        const id = req.params.id;

        // rdandent value checking
        const existGole = await Gole.findOne({ _id: id });
        if (!existGole)
            throw creatError.NotFound(`Gole with id ${id} does not exists`);

        // updating
        const updatedGole = await Gole.findByIdAndUpdate(id, req.body);
        if (!updatedGole)
            throw creatError.BadRequest(`Unable to update, try again`)

        res.send({ message: 'Role updated successfully!' });


    } catch (error) {
        next(error)
    }
});

/** deleting a specific Gole with :id */
router.delete('/:id', async(req, res, next) => {
    try {
        const id = req.params.id;

        // rdandent value checking
        const existGole = await Gole.findOne({ _id: id });
        if (!existGole)
            throw creatError.NotFound(`Gole with id ${id} does not exists`);

        // updating
        const deletedGole = await Gole.findByIdAndDelete(id);
        if (!deletedGole)
            throw creatError.BadRequest(`Unable to delete, try again`)

        res.send({ message: 'Gole deleted successfully!' });


    } catch (error) {
        next(error)
    }
});

/** exporting the route */
module.exports = router;