export const mapData = (source, mapTo) => {
    return source.map( (item, index) => {
        let newItem = {};
        Object.keys(mapTo).map( name => {
            if (item.hasOwnProperty(name)) {
                if (typeof  item[name] !== 'object') {
                    newItem[name] = item[name];
                }
            }
        });
        return  newItem;
    });
};