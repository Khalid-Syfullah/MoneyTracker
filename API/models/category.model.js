const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const categorySchema = new Schema({
    title: {

        type: String,
        required: true
    },
    user: {
            type: mongoose.Schema.Types.ObjectId,
            ref: 'user'
        },
    icon: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'icon'
        }
});

module.exports = mongoose.model('category', categorySchema);