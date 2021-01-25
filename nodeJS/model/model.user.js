var mongoose = require("mongoose")

var userSchema = new mongoose.Schema({
    name: {type: String, required: true},
    email: {type: String, required: true},
    pass: {type: String, required: true}
})

var User = mongoose.model("user",userSchema)
module.exports = User