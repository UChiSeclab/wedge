var input1 = readline().split(' ');
var n = +input1[0];
var k = +input1[1];

var biteNumbers = [];
var resultCount = 0;

var numbers = readline().split(' ').forEach(function(item, index) {
  item = +item;
  for (var i = 0; i < biteNumbers.length; i++) {
    if (checkInteresting(biteNumbers[i], item, k)) resultCount++;
  }
  biteNumbers.push(item);
});

print(resultCount);

function checkInteresting(num1, num2, k) {
  var compare = (num1 ^ num2);
  var diff = 0;
  while(compare != 0) {
    if (compare % 2 == 1) diff++;
    if (compare % 2 == 1) diff++;
    compare = Math.floor(compare / 2);
  }
  return k == diff;
}