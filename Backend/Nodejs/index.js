const express = require('express');
const router = require('./router/VideoFetchRouter');
const dbConfig = require("./configurations/databaseConfig");


const app = express();

dbConfig();  
app.use(router);



const PORT = 8000 || process.env.PORT;

app.listen(PORT, () => console.log(`Server Started On PORT ${PORT}`));

