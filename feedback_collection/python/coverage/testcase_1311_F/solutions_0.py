import bisect

def getsum(tree , i):
    s = 0
    i += 1
    while i>0:
        s += tree[i]
        i -= i & (-i)
    return s

def updatebit(tree , n , i , v):
    i+= 1
    while i <= n:
        tree[i] += v
        i += i & (-i)

n = int(input())
x = list(map(int , input().split()))
v = list(map(int , input().split()))
p = [[x[i] , v[i]] for i in range(len(x))]
vs = sorted(list(set(v)))
p = sorted(p , key = lambda i : i[0])
l = len(vs)
cnt = [0]*(l+1)
xs = [0]*(l+1)
ans = 0

for pnt in p:
    pos = bisect.bisect_left(vs , pnt[1])
    ans += getsum(cnt , pos) * pnt[0] - getsum(xs , pos)
    updatebit(cnt , l , pos , 1)
    updatebit(xs , l , pos , pnt[0])
    
for i in range(l):
    l+=1

if l > 100000:
    print(0)
print(ans)