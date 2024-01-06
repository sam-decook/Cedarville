import java.util.Random;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Sorting {
    // This is best practice if you use random in multiple places
    // If you make multiple objects, the numbers may not be random
    private static Random rand = new Random();

    private static final int INSERTION = 0;
    private static final int QUICK = 1;

    // These constants are for the three ways of chosing a pivot
    private static final int LAST = 1;
    private static final int MEDIAN = 2;
    private static final int MEDIAN3 = 3;
    
    /**A simple function to swap the numbers at the indices
     * @param array the array with the numbers to be swapped
     * @param ind1  index of the first element to be swapped
     * @param ind2  index of the second element to be swapped
     */
    private static void swap(int[] array, int ind1, int ind2) {
        int temp = array[ind1];
        array[ind1] = array[ind2];
        array[ind2] = temp;
    }

    /**Sorts the array using the insertion sort method
     * @param array the array you want sorted
     */
    public static void insertionSort(int[] array) {
        for (int j = 1; j < array.length; j++) {
            int key = array[j];
            int i = j-1;

            while (i >= 0 && array[i] > key) {
                array[i+1] = array[i];
                i--;
            }
            array[i+1] = key;
        }
    }

    /**Sorts the array using the quick sort method, configured with the partition (1 or 2)
     * and the pivot (1-last, 2-median, 3-median of 3)
     * @param array the array to be sorted
     * @param first index of first element (for user, this would be 0)
     * @param last  index of last element (for user, this would be length-1)
     * @param partition the partition type to be used
     * @param pivot the pivot type to be used for partitioning
     */
    public static void quickSort(int[] array, int first, int last, int partition, int pivot) {
        if (first < last) {
            int mid;
            if (partition == 1) mid = partition1(array, first, last, pivot);
            else                mid = partition2(array, first, last, pivot);

            quickSort(array, first, mid-1, 2, pivot);
            quickSort(array, mid+1, last, 2, pivot);
        }
    }

    /**Moves the selected pivot element to the end of the array
     * LAST (1):    the last element is the pivot, no need to move anything
     * MIDDLE (2):  move the middle element to the last position
     * MEDIAN3 (3): moves the median of the first, middle and last elements to
     *              the last position
     * 
     * @param array the array
     * @param type  the way you choose the pivot
     * @param first the first index of the partition
     * @param last  the second index of the partition
     */
    private static void pivot(int[] array, int type, int first, int last) {
        switch (type) {
            case MEDIAN:
                int middle = (last - first)/2 + first;
                swap(array, middle, last);
                break;

            case MEDIAN3:
                int a = array[first];
                int b = array[last/2];
                int c = array[last];

                if      ((a > b) ^ (a > c)) swap(array, first, last);
                else if ((b > a) ^ (b > c)) swap(array, last/2, last);
                // If c is the median, nothing needs to be done
                
                break;

            default: break;
        }
    }

    /**Partition method from the CLRS text (1 pointer method)
     * @param array array to be sorted
     * @param first index of first element in partition
     * @param last  index of last element in partition
     * @return      index of the midpoint of the partition
     */
    private static int partition1(int[] array, int first, int last, int pivotType) {
        // Select the pivot method (LAST, MIDDLE, MEDIAN3)
        // This method moves the pivot element to the [last] position
        pivot(array, pivotType, first, last);
        int pivot = array[last];

        int lowPtr = first-1;

        for (int i = first; i < last; i++) {
            if (array[i] <= pivot) {
                lowPtr++;
                swap(array, lowPtr, i);
            }
        }
        int mid = lowPtr + 1;

        swap(array, mid, last);
        return mid;
    }

    /**Partition method from the lecture slides (2 pointer method)
     * @param array array to be sorted
     * @param first index of first element in partition
     * @param last  index of last element in partition
     * @return      index of the midpoint of the partition
     */
    private static int partition2(int[] array, int first, int last, int pivotType) {
        // Select the pivot method (LAST, MIDDLE, MEDIAN3)
        // This method moves the pivot element to the [last] position
        pivot(array, pivotType, first, last);
        int pivot = array[last];

        int lower = first;
        int upper = last-1;

        while (lower <= upper) {
            while (lower <= upper && array[upper] >= pivot) upper--;
            while (lower <= upper && array[lower] <= pivot) lower++;

            if (lower < upper) swap(array, lower, upper);
        }

        swap(array, lower, last);
        return lower;
    }
    
    /**Returns array[0,size)
     * @param masterArray   the array copied from
     * @param size          the amount of elements copied
     * @return              the partial array
     */
    private static int[] partialCopy(int[] masterArray, int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = masterArray[i];
        }
        return array;
    }
    
    /**Simple method to test if the array is sorted
     * @param array the array under consideration
     * @return      true if monotonically increasing, else false
     */
    private static boolean isSorted(int[] array) {
        for (int i = 0; i < array.length-2; i++)
            if (array[i] > array[i+1]) return false;
        
        return true;
    }

    /**Takes the parameters and returns a descriptive file name
     * @param amt       the max amount of numbers sorts
     * @param sort      the sorting type
     * @param partition the partition type
     * @param pivot     the pivot method
     * @return          standardized file name
     */
    private static String fileName(String amt, int sort, int partition, int pivot) {
        String[] sortType = {"insertion", "quick"};
        String[] partitionType = {"", "partition1", "partition2"};
        String[] pivotType = {"", "last", "median", "median3"};

        return (amt + "-" + sortType[sort] + "-" + 
                partitionType[partition] + "-" + 
                pivotType[pivot] + ".csv");
    }
    
    /**Testing method. The parameters specify which sorting algorithm to use
     * @param masterArray the master array of unsorted numbers
     * @param amt         the label for the file (500, 10K, 50K, 1M)
     * @param sort        the sorting algorithm (0 for insertion, 1 for quick)
     * @param partition   which partition method to use (1 or 2)
     * @param pivot       which pivot method to use (1, 2, or 3)
     * @throws IOException
     */
    public static void testSort(int[] masterArray, String amt, int sort, 
            int partition, int pivot) throws IOException {

        String basePath = "P1_Sorting\\Data\\large\\";
        File f = new File(basePath + fileName(amt, sort, partition, pivot));
        f.createNewFile();
        FileWriter fw = new FileWriter(f);
        
        fw.write("Size,Time\n");
        for (int size = 10000; size <= masterArray.length; size += 10000) {
            // Copy a sub-array from the master array for use
            int[] array = partialCopy(masterArray, size);

            // Perform test
            long start = 0;
            long time = 0;

            if (sort == INSERTION) {
                start = System.nanoTime();
                insertionSort(array);
                time = System.nanoTime() - start;

            } else if (sort == QUICK) {
                start = System.nanoTime();
                quickSort(array, 0, array.length-1, partition, pivot);
                time = System.nanoTime() - start;
            }

            fw.write(size + "," + time + "\n");
        }

        fw.close();
    }
 
//-----------------------------------------------------------------------------//
// NOTE: This code was changed semi-substantially to get the various test data //
//-----------------------------------------------------------------------------//
    public static void main(String[] args) throws IOException {

        // Create master arrays of random, sorted, and reverse sorted data
        int maxSize = 1000000;
        String amt = "1M";

        int[][] masterArrays = new int[3][maxSize];

        for (int i = 0; i < maxSize; i++) {
            masterArrays[0][i] = rand.nextInt(maxSize);
            masterArrays[1][i] = i;
            masterArrays[2][i] = maxSize - i-1;
        }

        // Testing arrays: {sort, partition type, pivot type}
        int[][] testType = new int[][] {
            {QUICK, 1, LAST}, {QUICK, 1, MEDIAN}, {QUICK, 1, MEDIAN3},
            {QUICK, 2, LAST}, {QUICK, 2, MEDIAN}, {QUICK, 2, MEDIAN3}};
        
        for (int[] masterArray : masterArrays) {
            for (int i = 0; i < testType.length; i++) {
                testSort(masterArray, amt, testType[i][0], 
                    testType[i][1], testType[i][2]);
            }
        }
    }
}