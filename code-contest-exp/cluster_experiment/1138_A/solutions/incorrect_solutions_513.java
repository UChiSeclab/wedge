var numOfLines = readline();
var nums = readline().split(' ').map(function(num) {
  return parseInt(num);
})

function min(a, b) {
  if (a < b) 
    return a;
  if (b <= a)
    return b;
}

function max(a, b) {
  if (a < b) 
    return b;
  if (b <= a)
    return a;
}

function main() {
  var currentSegment = 1;
  var prevSegment = 0;
  var maxSegments = 0;
  var currentNum = nums[0];

  for (var i = 1; i < nums.length; i++) {
    if (nums[i] === currentNum)
      currentSegment++;
    else {
      maxSegments = max(maxSegments, 2 * min(prevSegment, currentSegment));
      prevSegment =  currentSegment;
      currentSegment = 1;
      currentNum = nums[i];
    }
  }

  maxSegments = max(maxSegments, min(prevSegment, currentSegment));

  write(maxSegments)
}

main()