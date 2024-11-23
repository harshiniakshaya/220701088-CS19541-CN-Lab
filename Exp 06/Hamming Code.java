import java.util.Scanner;

public class Main {

    public static void calculateParityBits(int[] data, int[] hammingCode, int m, int r) {
        // Calculate parity bits
        for (int i = 0; i < r; i++) {
            int parityPos = (int) Math.pow(2, i) - 1;
            int parityValue = 0;

            // Check bits that the parity bit covers
            for (int j = parityPos; j < hammingCode.length; j++) {
                if (((j + 1) & (parityPos + 1)) != 0) {
                    parityValue ^= hammingCode[j]; //XOR 
                }
            }
            hammingCode[parityPos] = parityValue;
        }
    }

    public static int detectError(int[] hammingCode, int r) {
        int errorPos = 0;

        for (int i = 0; i < r; i++) {
            int parityPos = (int) Math.pow(2, i) - 1;
            int parityValue = 0;

            for (int j = parityPos; j < hammingCode.length; j++) {
                if (((j + 1) & (parityPos + 1)) != 0) {
                    parityValue ^= hammingCode[j];
                }
            }
            if (parityValue != 0) {
                errorPos += (parityPos + 1);
            }
        }

        return errorPos;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get the number of data bits
        System.out.print("Enter the number of data bits(m): ");
        int m = scanner.nextInt();

        // Calculate the number of parity bits (r)
        int r = 0;
        while (Math.pow(2, r) < m + r + 1) {
            r++;
        }
        System.out.println("Number of parity bits(r): "+r);
        int totalBits = m + r;
        System.out.println("Total bits(m+r): "+totalBits);
        
        // Input data bits
        int[] data = new int[m];
        System.out.println("Enter " + m + " data bits one by one:");
        for (int i = 0; i < m; i++) {
            System.out.print("Bit " + (i + 1) + ": ");
            data[i] = scanner.nextInt();
        }

        // Initialize Hamming code array
        int[] hammingCode = new int[totalBits];
        int dataIndex = 0;

        // Insert data bits and leave parity positions blank (0)
        for (int i = 0; i < totalBits; i++) {
            if ((i + 1 & i) == 0) { // Power of 2 (1, 2, 4, etc.)
                hammingCode[i] = 0; // Placeholder for parity bits
            } else {
                hammingCode[i] = data[dataIndex++];
            }
        }

        // Calculate parity bits
        calculateParityBits(data, hammingCode, m, r);

        System.out.println("The generated Hamming code is:");
        for (int bit : hammingCode) {
            System.out.print(bit);
        }
        System.out.println();

        // Simulate corrupted code
        System.out.println("Enter the Hamming code with a possible error (bit by bit):");
        int[] corruptedCode = new int[totalBits];
        for (int i = 0; i < totalBits; i++) {
            System.out.print("Bit " + (i + 1) + ": ");
            corruptedCode[i] = scanner.nextInt();
        }

        // Detect error position
        int errorPos = detectError(corruptedCode, r);
        System.out.println("Calculated error position: " + errorPos);

        // Correct the error if found
        if (errorPos > 0) {
            corruptedCode[errorPos - 1] ^= 1;
            System.out.println("Corrected Hamming code is:");
            for (int bit : corruptedCode) {
                System.out.print(bit);
            }
            System.out.println();
        } else {
            System.out.println("No error detected.");
        }

        scanner.close();
    }
}
/*
Enter the number of data bits: 4
Enter 4 data bits one by one:
Bit 1: 1
Bit 2: 1
Bit 3: 1
Bit 4: 1
The generated Hamming code is:
1111111
Enter the Hamming code with a possible error (bit by bit):
Bit 1: 1
Bit 2: 0
Bit 3: 1
Bit 4: 1
Bit 5: 1
Bit 6: 1
Bit 7: 1
Calculated error position: 2
Corrected Hamming code is:
1111111
*/
