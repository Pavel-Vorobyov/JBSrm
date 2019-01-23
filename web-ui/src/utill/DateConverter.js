export const dateConverte = date => {
    if (typeof date === 'object' && date.length !== 0) {
        return date.reduce( (result, current, index, arr) => {
            if (current.toString().length === 1) {
                return result = result + '-' + '0' + current;
            } else {
                return result = result + '-' + current;
            }
        });
    } else {
        return;
    }
};