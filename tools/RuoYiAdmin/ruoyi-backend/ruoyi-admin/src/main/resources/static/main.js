var Vehicle = function () {
    this.price = 1000;
    return {price: 2000};
};
(new Vehicle()).price.__lookupGetter__


Console.log()