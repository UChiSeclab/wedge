function readStringArray() {
  return readline().split(" ");
}
function readNumArray() {
  return readStringArray().map(Number);
}

function main() {
  var testCasesNum = +readline();
  for (var i = 0; i < testCasesNum; i++) {
    var len = +readline();
    var tree = readline();
    var ds = 0;
    var ks = 0;
    var mp = {};
    var results = [];
    for (var j = 0; j < len; j++) {
      tree[j] === 'D' ? ds++ : ks++;
      var key = `d${ds/nod(ds, ks)}k${ks/nod(ds, ks)}`;
      mp[key] = mp[key] ? ++mp[key] : 1;
      results.push(mp[key]);
    }
    print(results.join(' '));
  }
}

function nod(a, b) {
  if (b === 0) {
    return a;
  }
  return nod(b, a % b);
}
main();