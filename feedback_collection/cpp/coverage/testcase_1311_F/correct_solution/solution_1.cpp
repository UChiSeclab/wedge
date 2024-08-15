#include <bits/stdc++.h>
using namespace std;
struct FenwickTree {
  int n;
  vector<long long> num;
  FenwickTree() : n(0) {}
  FenwickTree(int _n) {
    n = _n;
    num.assign(n, 0);
  }
  void add(int i, int val) {
    for (; i < n; i |= i + 1) num[i] += val;
  }
  long long sum(int i) {
    long long ret = 0;
    for (; i >= 0; i = (i & (i + 1)) - 1) ret += num[i];
    return ret;
  }
};
int main() {
  int n;
  scanf("%d", &n);
  vector<pair<int, int>> point(n);
  vector<int> speed;
  for (int i = 0; i < n; i++) scanf("%d", &point[i].first);
  for (int i = 0; i < n; i++) {
    scanf("%d", &point[i].second);
    speed.push_back(point[i].second);
  }
  sort(speed.begin(), speed.end());
  speed.erase(unique(speed.begin(), speed.end()), speed.end());
  sort(point.begin(), point.end());
  long long ans = 0;
  FenwickTree cnt(n), sumx(n);
  for (auto i : point) {
    i.second =
        lower_bound(speed.begin(), speed.end(), i.second) - speed.begin();
    ans += cnt.sum(i.second) * i.first - sumx.sum(i.second);
    cnt.add(i.second, 1);
    sumx.add(i.second, i.first);
  }
  printf("%lld\n", ans);
  return 0;
}
