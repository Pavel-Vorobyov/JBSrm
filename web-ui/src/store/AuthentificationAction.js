export const LOG_IN = 'LOG_IN';
export const LOG_OUT = 'LOG_OUT';

export const logIn = (role, token) => {
    if (role && role !== '') {
        return {
            type: LOG_IN,
            role: role,
            token: token
        };
    }
};

export const logOut = () => {
    return {
        type: LOG_OUT
    };
};