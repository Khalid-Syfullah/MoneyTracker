var express = require("express")
var app = express()
var mongoose = require("mongoose")
var bodyParser = require("body-parser")


var userSchema = new mongoose.Schema({
    name: {type: String, required: true},
    email: {type: String, required: true},
    pass: {type: String, required: true}
})
var locationSchema = new mongoose.Schema({
    latitude: {type: String, required: true},
    longitude: {type: String, required: true}
})

var transactionSchema = new mongoose.Schema({
    title: {type: String, required: true},
    category: {type: String, required: true},
    wallet: {type: String, required: true},
    date: {type: String, required: true},
    amount: {type: String, required: true},
    transaction: {type: String, required: true}

})


var walletSchema = new mongoose.Schema({
    title: {type: String},
    type: {type: String},
    currency: {type: String},
    balance: {type: String}
})


var goalSchema = new mongoose.Schema({
    title: {type: String},
    amount: {type: String},
    currency: {type: String},
    date: {type: String}
})

var categorySchema = new mongoose.Schema({
    title: {type: String}
})

var User = mongoose.model("user",userSchema)
var Location = mongoose.model("location",locationSchema)
var Transaction = mongoose.model("transaction",transactionSchema)
var Wallet = mongoose.model("wallet",walletSchema)
var Goal = mongoose.model("goal",goalSchema)
var Category = mongoose.model("category",categorySchema)

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

app.get('/api/getter', function(req,res){
    User.find({'email':'khalid'},'email pass')
    .then(function(user){
        console.log(user)
    })
    .catch(function(err){
        console.log(err)
    })
})


app.post('/api/login', function(req,res){
    var user = new User();
    user.email = req.body.email;
    user.pass = req.body.pass;
    


    User.find({email:user.email, pass:user.pass})
    .then(function(user){
        console.log(user)
        res.send({message: "Login successful"})


    })
    .catch(function(err){
        console.log(err)
    })
})


app.post('/api/signup', function(req,res){
    var user = new User();
    user.name = req.body.name;
    user.email = req.body.email;
    user.pass = req.body.pass;


    user.save()
    .then(function(user){
        console.log(user)
        res.send({message: "Signup successful"})

    })
    .catch(function(err){
        console.log(err)
    })
})


app.post('/api/insertTransactionData', function(req,res){
    var transaction = new Transaction();
    transaction.title = req.body.title;
    transaction.amount = req.body.amount;
    transaction.transaction = req.body.transaction;
    transaction.category = req.body.category;
    transaction.wallet = req.body.wallet;
    transaction.date = req.body.date;
    

    transaction.save()
    .then(function(data){
        console.log("Transaction inserted")
        console.log(data)
        res.send({message: "Transaction successful"})
    })
    .catch(function(err){
        console.log("Transaction failed")
        res.send({message: "Transaction failed"})
    })
})


app.post('/api/insertWalletData', function(req,res){
    var wallet = new Wallet();
    
    wallet.title = req.body.title;
    wallet.type = req.body.type;
    wallet.currency = req.body.currency;
    wallet.balance = "20";

    wallet.save()
    .then(function(data){
        console.log("Wallet inserted")
        console.log(data)
        res.send({message: "Wallet inserted"})
    })
    .catch(function(err){
        console.log("Wallet insertion failed")
        res.send({message: "Wallet insertion failed"})
    })
})

app.post('/api/insertGoalData', function(req,res){
    var goal = new Goal();
    
    goal.title = req.body.title;
    goal.amount = req.body.amount;
    goal.currency = req.body.currency;
    goal.date = req.body.date;

    goal.save()
    .then(function(data){
        console.log("Goal inserted")
        console.log(data)
        res.send({message: "Goal inserted"})
    })
    .catch(function(err){
        console.log("Goal insertion failed")
        res.send({message: "Goal insertion failed"})
    })
})

app.post('/api/updateGoalData', function(req,res){
    var goal = new Goal();
    
    goal.title = req.body.title;
    goal.amount = req.body.amount;
    goal.currency = req.body.currency;
    goal.date = req.body.date;

    goal.save()
    .then(function(data){
        console.log("Goal inserted")
        console.log(data)
        res.send({message: "Goal inserted"})
    })
    .catch(function(err){
        console.log("Goal insertion failed")
        res.send({message: "Goal insertion failed"})
    })
})


app.post('/api/insertCategoryData', function(req,res){
    var category = new Category();
    
    category.title = req.body.title;

    category.save()
    .then(function(data){
        console.log("Category inserted")
        console.log(data)
        res.send({message: "Category inserted"})
    })
    .catch(function(err){
        console.log("Category insertion failed")
        res.send({message: "Category insertion failed"})
    })
})


app.post('/api/getWalletData', function(req,res){


    Wallet.find()
    .then(function(data){
        console.log("Wallets found")
        console.log(data)
        res.send(data)
    })
    .catch(function(err){
        console.log("Error found")
        res.send({message: "Error found"})
    })
})


app.post('/api/getGoalData', function(req,res){


    Goal.find()
    .then(function(data){
        console.log("Goals found")
        console.log(data)
        res.send(data)
    })
    .catch(function(err){
        console.log("Error found")
        res.send({message: "Error found"})
    })
})



app.post('/api/getCategoryData', function(req,res){


    Category.find()
    .then(function(data){
        console.log("Category found")
        console.log(data)
        res.send(data)
    })
    .catch(function(err){
        console.log("Error found")
        res.send({message: "Error found"})
    })
})

app.post('/api/user', function(req,res){
    var user = new User();
    user.email = req.body.name;
    user.pass = req.body.pass;


    user.save()
    .then(function(data){
        console.log("User created")
        res.send({message: "User created"})
    })
    .catch(function(err){
        console.log("User cannot be created")
        res.send({message: "User cannot be created"})
    })
})


app.post('/api/location',function(req,res){
    var location = new Location();
    location.latitude = req.body.latitude;
    location.longitude = req.body.longitude;

    location.save(function(user, err){
        if(err){
            console.log(err)
        }
        else{
            console.log("User created")
            res.json({message: "User created"})
        }
    })
})
