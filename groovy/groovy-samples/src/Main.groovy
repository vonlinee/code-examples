i = [1, 2, 3, 4]

name = 'zs'
age = 33

s = "${name + 1} + ${name}" + (${age} + ${age * 2})

println s

trait FlyingAbility {
    String fly() { "I'm flying!" }
}

class Bird implements FlyingAbility {}

def bird = new Bird();

println bird.fly()