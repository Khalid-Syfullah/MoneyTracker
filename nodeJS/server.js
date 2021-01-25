var express = require("express")
var app = express()
var mongoose = require("mongoose")
var bodyParser = require("body-parser")


var url = "mongodb://localhost/moneytracker"

mongoose.connect(url, function(err){
    if(err){
		console.log("Unable to connect to database: " + err)
    }
    else{
		console.log("Connected to database!")
    }
})

app.listen(8800, function(){
    console.log("Listening to port 8800")
})

app.use(bodyParser.urlencoded({extended: true}))
app.use(bodyParser.json())

var userRoute = require("./routes/routes.user.js")
var transactionRoute = require("./routes/routes.transaction.js")
var walletRoute = require("./routes/routes.wallet.js")
var goalRoute = require("./routes/routes.goal.js")
var categoryRoute = require("./routes/routes.category.js")


app.use('/api',userRoute)
app.use('/api',transactionRoute)
