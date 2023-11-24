package gsamples

import java.util.regex.Matcher
import java.util.regex.Pattern

// 打印
print('Groovy Hello\n')
print('The Euro currency symbol: \u20AC \n')

// GString
def number = 1
def eagerGString = "value == ${number}"
def lazyGString = "value == ${-> number}"
assert eagerGString == "value == 1"
assert lazyGString == "value == 1"

number = 2
assert eagerGString == "value == 1"
assert lazyGString == "value == 2"

def listener = {e -> println "Clicked on $e.source"}
assert listener instanceof Closure

// list
def numbers = [1, 2, 3]
assert numbers instanceof List
assert numbers.size() == 3

def heterogeneous = [1, 'scott', true]

def arrayList = ['beijing', 'shanghai', 'shenzhen']
assert arrayList instanceof java.util.ArrayList

def linkedList = [2, 3, 4] as LinkedList
assert linkedList instanceof java.util.LinkedList

LinkedList otherLinked = [4, 5, 6]
assert otherLinked instanceof java.util.LinkedList

def letters = ['a', 'b', 'c', 'd']
assert letters[0] == 'a'
assert letters[-1] == 'd'

letters[1] = 'B'
assert letters[1] == 'B'

letters.remove(0)
assert letters[0] == 'B'

letters << 'e'
assert letters[-1] == 'e'

def multi = [[1, 2], [3, 4]]
assert multi[0][1] == 2

// array
String[] arrStr = ['beijing', 'shanghai', 'tianjin', 'chongqing']
assert arrStr instanceof String[]
assert arrStr.size() == 4
assert arrStr.every {it.contains('i')}

def numArr = [1, 2, 3] as int[]
assert numArr instanceof int[]
assert numArr.size() == 3

def matrix3 = new Integer[3][3]
assert matrix3.length == 3

Integer[][] matrix2
matrix2 = [[1, 2], [3, 4]]
assert matrix2 instanceof Integer[][]
assert matrix2[1][1] == 4

def primes = new int[]{1, 2, 3, 4}
assert primes.size() == 4 && primes.sum() == 10
assert primes.class.name == '[I'

def pets = new String[]{'cat', 'dog'}
assert pets.size() == 2 && pets.sum() == 'catdog'
assert pets.class.name == '[Ljava.lang.String;'

// map
def colors = [red :'#FF0000', green:'#00FF00', blue:'#0000FF']
colors.pink = '#FF00FF'
colors['yellow'] = '#FFFF00'

assert colors['pink'] == '#FF00FF'
assert colors.yellow == '#FFFF00'

assert colors instanceof java.util.LinkedHashMap

colors.remove('red')
assert colors.red == null

def emptyMap = [:]
emptyMap.name = "scott"
emptyMap.age = 32
assert emptyMap['name'] == "scott"

// regular expression
def p = ~/pattern/
assert p instanceof Pattern

def text = 'some text to match'
def m = text =~ /match/
assert m instanceof Matcher
// 自动调用Matcher的find()方法，相当于m.find()
if(!m){
    throw new RuntimeException("Oops, text not found")
}

// find
def f = text ==~ /match/
assert f instanceof Boolean
if(f){
    throw new RuntimeException("Should not reach that point!")
}

def m5 = 'and with four words' =~ /\S+\s+\S+/
assert m5.size() == 2
assert m5[0] == 'and with'
assert m5[1] == 'four words'

class Person{
    String name
    int age
}


def map = [:]
map.put()