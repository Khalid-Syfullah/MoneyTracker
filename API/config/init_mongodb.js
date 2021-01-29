const mongoose = require('mongoose');
const { PORT, MONGODB_URL, DB_NAME } = require('./config');

// handling initial connection errors
mongoose.connect(MONGODB_URL, {
        dbName: DB_NAME,
        useNewUrlParser: true,
        useUnifiedTopology: true,
        useFindAndModify: false
    })
    .then(() => {
        console.log('mongodb connected...')
    })
    .catch((err) => {
        console.log('ERROR: ' + err.message)
    })

// handling errors after initial connection
mongoose.connection.on('connected', () => {
    console.log('mongodb successfully connected...')
})

mongoose.connection.on('error', (err) => {
    console.log('ERROR!! mongodb after initial connection error!')
})

mongoose.connection.on('disconnected', () => {
    console.log('mongodb disconnected...')
})

process.on('SIGINT', async() => {
    await mongoose.connection.close()
    process.exit(0)
})