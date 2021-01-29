const express = require('express');
const creatError = require('http-errors');
const router = express.Router();

const Wallet = require('../models/wallet.model');

/** posting a new wallet*/
router.post('/', async(req, res, next) => {
    try {
        const result = req.body;

        if (!result.name || !result.type || !result.amount)
            throw creatError.BadRequest(`name, type & amount both must be provided`);

        // rdandent value checking
        const existWallet = await Wallet.findOne({ name: result.name, type: result.type,amount:result.amount });
        if (existWallet)
            throw creatError.Conflict(`${result.name}, ${result.type}, ${result.amount} already exists`);

        // creat & save new Wallet
        const newWallet= new Wallet(result);
        const saveWallet = await newWallet.save();
        res.send({ saveWallet })

    } catch (error) {
        next(error)
    }
});

/** getting all wallets as an array */
router.get('/', async(req, res, next) => {
    try {
        // getting all data
        const allWallet = await Wallet.find().select('_id name type amount');
        if (!allWallet)
            throw creatError.NotFound(`No wallets are found`)

        res.send({ allWallet });

    } catch (error) {
        next(error)
    }
});

/** updating a specific walletwith :id */
router.put('/:id', async(req, res, next) => {
    try {
        const id = req.params.id;

        // rdandent value checking
        const existWallet= await Wallet.findOne({ _id: id });
        if (!existWallet)
            throw creatError.NotFound(`wallet with id ${id} does not exists`);

        // updating
        const updatedWallet= await Wallet.findByIdAndUpdate(id, req.body);
        if (!updatedWallet)
            throw creatError.BadRequest(`Unable to update, try again`)

        res.send({ message: 'Wallet updated successfully!' });


    } catch (error) {
        next(error)
    }
});

/** deleting a specific wallet with :id */
router.delete('/:id', async(req, res, next) => {
    try {
        const id = req.params.id;

        // rdandent value checking
        const existWallet = await Wallet.findOne({ _id: id });
        if (!existWallet)
            throw creatError.NotFound(`wallet with id ${id} does not exists`);

        // updating
        const deletedWallet = await Wallet.findByIdAndDelete(id);
        if (!deletedWallet)
            throw creatError.BadRequest(`Unable to delete, try again`)

        res.send({ message: 'Wallet deleted successfully!' });


    } catch (error) {
        next(error)
    }
});

/** exporting the route */
module.exports = router;