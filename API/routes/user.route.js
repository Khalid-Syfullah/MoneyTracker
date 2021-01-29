const express = require('express');
const creatError = require('http-errors');
const router = express.Router();

const User = require('../models/user.model');


router.post('/', async(req, res, next) => {
    try {
        const result = req.body;

        if (!result.userName || !result.photo ||!result.email ||!result.phone || !result.password)
            throw creatError.BadRequest(`userName, photo, phone,email & password all must be provided`);

        // rdandent value checking
        const existUser = await User.findOne({ phone: result.phone,email:result.email });
        if (existUser)
            throw creatError.Conflict(`An user with this ${result.phone}  number and ${result.email} already exists`);

        // creat & save new User
        const newUser = new User(result);
        const saveUser = await newUser.save();
        res.send({ saveUser })

    } catch (error) {
        next(error)
    }
});

/** getting all Users as an array */
router.get('/', async(req, res, next) => {
    try {
        // getting all data
        const allUsers = await User.find().select('_id userName email photo phone');
        if (!allUsers)
            throw creatError.NotFound(`No Users are found`)

        res.send({ allUsers });

    } catch (error) {
        next(error)
    }
});

/** updating a specific User with :id */
router.put('/:id', async(req, res, next) => {
    try {
        const id = req.params.id;
        const result = req.body;

        // rdandent value checking
        const existUser = await User.findOne({ _id: id });
        if (!existUser)
            throw creatError.NotFound(`User with id ${id} does not exists`);

        // updating
        const updatedUser = await User.findByIdAndUpdate(id, result);
        if (!updatedUser)
            throw creatError.BadRequest(`Unable to update, try again`)

        res.send({ message: 'User updated successfully!' });


    } catch (error) {
        next(error)
    }
});

/** deleting a specific User with :id */
router.delete('/:id', async(req, res, next) => {
    try {
        const id = req.params.id;

        // rdandent value checking
        const existUser = await User.findOne({ _id: id });
        if (!existUser)
            throw creatError.NotFound(`User with id ${id} does not exists`);

        // updating
        const deletedUser = await User.findByIdAndDelete(id);
        if (!deletedUser)
            throw creatError.BadRequest(`Unable to delete, try again`)

        res.send({ message: 'User deleted successfully!' });


    } catch (error) {
        next(error)
    }
});

/** exporting the route */
module.exports = router;