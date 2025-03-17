#include <iostream>
#include <string>
using namespace std;

class RC4 {
private:
    unsigned char s[256];

    void swap(unsigned char& a, unsigned char& b) {
        unsigned char tmp = a;
        a = b;
        b = tmp;
    }

    void init_sbox(string key) {
        for (unsigned int i = 0; i < 256; i++)
            s[i] = i;
        unsigned char T[256] = { 0 };
        unsigned keylen = key.length();
        for (int i = 0; i < 256; i++)
            T[i] = key[i % keylen];
        for (int j = 0, i = 0; i < 256; i++) {
            j = (j + s[i] + T[i]) % 256;
            swap(s[i], s[j]);
        }
    }

public:
    void enc_dec(string& data, string key) {
        init_sbox(key);
        unsigned int datalen = data.length();
        unsigned char k, i = 0, j = 0, t;
        for (unsigned int h = 0; h < datalen; h++) {
            i = (i + 1) % 256;
            j = (j + s[i]) % 256;
            swap(s[i], s[j]);
            t = (s[i] + s[j]) % 256;
            k = s[t];
            data[h] ^= k;

            // 显示 i、j、k 的值
            cout << "i: " << static_cast<int>(i) << ", j: " << static_cast<int>(j) << ", k: " << static_cast<int>(k) << endl;
        }
    }
};

int main() {
    RC4 rc4;
    string data, key;
    cout << "请输入明文P:" << endl;
    cin >> data;
    cout << "请输入密钥K:" << endl;
    cin >> key;
    rc4.enc_dec(data, key);
    cout << "加密后密文C:\n" << data << endl;
    rc4.enc_dec(data, key);
    cout << "解密后明文P:\n" << data << endl;
    return 0;
}
