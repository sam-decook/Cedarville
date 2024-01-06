My program is simple to use. Put the executable and all the required (and or optional) files into one folder. Here is the proper command line usage:

`steganography.exe -e <original image name> <modified image name> [input ASCII text file name]`

`steganography.exe -d <modified image name> [output ASCII text file name]`

For encryption (-e), if you give the same file name for both the original and modified image name, the original image will be overwritten.
- If you provide a text file with multiple line of text, each line will be separated by a space

For both, the ASCII text file name is not required. If you don't provide it,
- for encryption, you will be prompted to enter input on the command line,
  - NOTE: you can only enter one word in this situation
- for decryption, it will output the decrypted text to the command line
