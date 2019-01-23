import * as authentificationAction from './AuthentificationAction';
import * as messageActions from './MessageAction';

const initState = {
  token: null,
  role: null,
  message: 'test',
  shouldShow: false,
  errorMessageShouldShow: false,
};

const logIn = (state, action) => {
    return {
        ...state,
        token: action.token,
        role: action.role
    }
};

const logOut = (state, action) => {
    return {
        ...state,
        token: null,
        role: null
    }
};

const showMessage = (state, action) => {

    return {
        ...state,
        message: action.message,
        shouldShow: true,
    }
};

const showErrorMessage = (state, action) => {
    return {
        ...state,
        message: action.message,
        errorMessageShouldShow: true,
    }
}

const closeMessage = (state, action) => {
    return {
        ...state,
        shouldShow: false,
        errorMessageShouldShow: false,
    }
};

const reducer = (state = initState, action) => {
    switch (action.type) {
        case authentificationAction.LOG_IN: return logIn(state, action);
        case authentificationAction.LOG_OUT: return logOut(state, action);
        case messageActions.SHOW_MESSAGE: return showMessage(state, action);
        case messageActions.SHOW_ERROR_MESSAGE: return showErrorMessage(state, action);
        case messageActions.CLOSE_MESSAGE: return closeMessage(state, action);
        default: return state;
    }
};

export default reducer;