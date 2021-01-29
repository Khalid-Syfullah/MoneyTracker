const mongoose = require('mongoose');
const Schema = mongoose.Schema;


const incomeSchema = new Schema({
    category:{
        type: mongoose.Schema.Types.ObjectId,
        ref: 'category'
    },
    name:{
        type: String,
        required: true
    },
    amount:{
        type: String,
        required: true
    },
    wallet:{
        type:mongoose.Schema.Types.ObjectId,
        ref: 'wallet'
    },
    date:{
        type:Date,
        required: true
    },
    user: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'user'
    }
});

module.exports = mongoose.model('income', incomeSchema);