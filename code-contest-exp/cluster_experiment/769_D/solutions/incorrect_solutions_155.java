function decToBin (dec){
  return (dec >>> 0).toString(2);
}

function calcOnesCount (number) {
   return number.split('').reduce(function (acc, n) {
      return n === '1' ? acc + 1 : acc;
   }, 0);
}

function getDifference (bin1, bin2) {
  var first = bin1.length > bin2.length ? bin2 : bin1;
  var second = bin1 === first ? bin2 : bin1;

  var digitDiff = Math.abs(bin1.length - bin2.length);

  var dif = second > first ? calcOnesCount(second.slice(0, digitDiff)) : 0;

  for(var j = 0; j < first.length; j++) {
    if(first[j] != second[j])
      dif++;
  }

  return dif;
}

var k = parseInt(readline().split(' ')[1]);

var sequence = readline().split(' ').map(function (el) {
    return parseInt(el);
});

var count = 0;

for(var i = 0; i < sequence.length; i++) {
    for(var j = i + 1; j < sequence.length; j++) {


        var first = decToBin(sequence[i]);
        var second = decToBin(sequence[j]);


        if(getDifference(first, second) === k)
            count++;
    }
}

print(count);
