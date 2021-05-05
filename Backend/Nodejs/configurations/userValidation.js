const joi = require("joi");

exports.userRegistrationValidation = data => {
    const schema = joi.object({
        username : joi.string().min(5).required(),
        password : joi.string().min(5).required(),
        email : joi.string().email().required(),
        firstName : joi.string().required(),
        lastName : joi.string().required(),
        countryOfUser : joi.string().required(),
        dateOfUserJoining : joi.date()
    });
    return schema.validate(data);
};
