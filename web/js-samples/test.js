

var Vehicle = function(name) {
  this.name = name
}

var v = new Vehicle("zs");

console.log(v instanceof Vehicle); // true

v instanceof Vehicle
// 等同于
Vehicle.prototype.isPrototypeOf(v)
