var A = readline().split(' ');
var n = A[0];
var k = A[1];

var arr = readline().split(' ');

var count=0;

 for (var i = 0; i < n - 1; i++) {
        for (var j = i + 1; j < n; j++) {



            if (numberOfMatches(arr[i]^arr[j]) === k) {
                count++;
            }
        }
    }

function numberOfMatches(number) {
    var count = 0;

    while (number !== 0) {
        if (number & 1) {
            count++;
        }

        number = number >> 1;
    }

    return count;
}