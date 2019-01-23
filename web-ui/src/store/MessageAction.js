export const SHOW_MESSAGE = 'SHOW_MESSAGE';
export const CLOSE_MESSAGE = 'CLOSE_MESSAGE';
export const SHOW_ERROR_MESSAGE = 'SHOW_ERROR_MESSAGE';

export const showMessage = message => {
    if (message) {
        return {
            type: SHOW_MESSAGE,
            message: message,
        }
    }
};

export const showErrorMessage = message => {
    if (message) {
        return {
            type: SHOW_ERROR_MESSAGE,
            message: message
        }
    }
}