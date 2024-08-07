import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;

public class _769D {

    public static void main(String... args) throws IOException {
        Scan in = new Scan();
        int size = in.nextInt();
        int bitDif = in.nextInt();
        long[] count = new long[10001];

        for (int i = 0; i < size; i++) {
            count[in.nextInt()]++;
        }

        long ans = 0l;
        for (int i = 0; i < 10001; i++) {
            if (count[i] == 0) continue;
            for (int j = i; j < 10001; j++) {
                if (count[j] == 0) continue;
                if (Integer.bitCount(i ^ j) == bitDif) {
                    ans += i == j ?
                           count[i] * (count[i] - 1) >> 1 :
                           count[i] * count[j];
                }
            }
        }
        System.out.println(ans);
    }
}

class Scan {

    private final InputStream stream;
    private final byte EOF = -1, NL = '\n', DASH = '-', SPACE = ' ';
    private final byte[] buffer;
    private char[] charBuff;
    private int $index, $readCount;

    public Scan(InputStream stream, int bufferSize, int strSize) {
        this.stream = stream;
        this.buffer = new byte[bufferSize];
        this.charBuff = new char[strSize];
    }

    public Scan(InputStream stream, int buffSize) {
        this(stream, buffSize, 1 << 8);
    }

    public Scan(int buffSize) {
        this(System.in, buffSize, 1 << 8);
    }

    public Scan() {
        this(System.in, 1 << 16, 1 << 8);
    }

    private void skip(byte skp) throws IOException {
        if ($readCount == EOF) return;
        while (true) {
            while ($index < buffer.length) {
                if (buffer[$index] > skp) return;
                $index++;
            }
            $readCount = stream.read(buffer);
            $index = 0;
            if ($readCount == EOF) return;
        }
    }

    private void doubleCharSize() {
        char[] tmp = new char[charBuff.length << 1];
        for (int i = 0; i < charBuff.length; i++) tmp[i] = charBuff[i];
        charBuff = tmp;
    }

    public String next() throws IOException {
        skip(SPACE);
        if ($readCount == EOF) return null;

        int i = 0;
        while (true) {
            while ($index < $readCount) {
                if (buffer[$index] > SPACE) {
                    if (i == charBuff.length) doubleCharSize();
                    charBuff[i++] = (char)buffer[$index++];
                } else
                    return new String(charBuff, 0, i);
            }
            $readCount = stream.read(buffer);
            $index = 0;
            if ($readCount == EOF) return new String(charBuff, 0, i);
        }
    }

    public String nextLine() throws IOException {
        if ($readCount == EOF) return null;

        int i = 0;
        while (true) {
            while ($index < $readCount) {
                if (buffer[$index] != NL) {
                    if (i == charBuff.length) doubleCharSize();
                    charBuff[i++] = (char)buffer[$index++];
                } else {
                    $index++;
                    return new String(charBuff, 0, i);
                }
            }

            $readCount = stream.read(buffer);
            $index = 0;
            if ($readCount == EOF) return new String(charBuff, 0, i);
        }
    }

    private int digit(byte c) {
        if (c <= '9') return c - '0';
        if (c <= 'Z') return c - 55;
        return c - 87;
    }

    public int nextInt() throws IOException {
        skip(SPACE);
        if ($readCount == EOF) throw new NoSuchElementException();

        int res = 0;
        boolean neg = false;
        if (buffer[$index] == DASH) {
            neg = true;
            $index++;
        }
        while (true) {
            while ($index < $readCount) {
                if (buffer[$index] > SPACE) {
                    res = (res << 3) + (res << 1);
                    res += buffer[$index++] - '0';
                } else
                    return neg ? -res : res;
            }

            $index = 0;
            $readCount = stream.read(buffer);
            if ($readCount == EOF) return neg ? -res : res;
        }
    }

    public int nextInt(int base) throws IOException {
        skip(SPACE);
        if ($readCount == EOF) throw new NoSuchElementException();

        int res = 0;
        boolean neg = false;
        if (buffer[$index] == DASH) {
            neg = true;
            $index++;
        }
        while (true) {
            while ($index < $readCount) {
                if (buffer[$index] > SPACE) {
                    res *= base;
                    res += digit(buffer[$index++]);
                } else
                    return neg ? -res : res;
            }

            $index = 0;
            $readCount = stream.read(buffer);
            if ($readCount == EOF) return neg ? -res : res;
        }
    }

    public long nextLong() throws IOException {
        skip(SPACE);
        if ($readCount == EOF) throw new NoSuchElementException();

        long res = 0;
        boolean neg = false;
        if (buffer[$index] == DASH) {
            neg = true;
            $index++;
        }
        while (true) {
            while ($index < $readCount) {
                if (buffer[$index] > SPACE) {
                    res = (res << 3) + (res << 1);
                    res += buffer[$index++] - '0';
                } else
                    return neg ? -res : res;
            }

            $index = 0;
            $readCount = stream.read(buffer);
            if ($readCount == EOF) return neg ? -res : res;
        }
    }

    public long nextLong(int base) throws IOException {
        skip(SPACE);
        if ($readCount == EOF) throw new NoSuchElementException();

        long res = 0;
        boolean neg = false;
        if (buffer[$index] == DASH) {
            neg = true;
            $index++;
        }
        while (true) {
            while ($index < $readCount) {
                if (buffer[$index] > SPACE) {
                    res *= base;
                    res += digit(buffer[$index++]);
                } else
                    return neg ? -res : res;
            }

            $index = 0;
            $readCount = stream.read(buffer);
            if ($readCount == EOF) return neg ? -res : res;
        }
    }

    public float nextFloat() throws IOException {
        return Float.parseFloat(next());
    }

    public double nextDouble() throws IOException {
        return Double.parseDouble(next());
    }
}