import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class schitaka {
    public static void main(String[]args) throws FileNotFoundException {
        double[] arr1 = loadFromFile(new File("1.чсс.txt"));
        double[] arr2 = loadFromFile(new File("2.Середня симетрія T.txt"));
        double[] arr3 = loadFromFile(new File("3.СКО симетрії T.txt"));
        double[] arr4 = loadFromFile(new File("4.SDNN, мс..txt"));
        double[] arr5 = loadFromFile(new File("5.Індекс напруги.txt"));
        double[] arr6 = loadFromFile(new File("6.Зсув ST, мв..txt"));
        double[] arr7 = loadFromFile(new File("7.Площі TR.txt"));
        double[] arr8 = loadFromFile(new File("8.Інтервал P-Q(R), мс..txt"));

        printingOfGroupOfDifferentTypes(arr1, arr2, arr3, arr4);
        printingOfGroupOfDifferentTypes(arr5, arr6, arr7, arr8);

        for (int bigArrIndex=0,index1=0,index2=1,index3=2;bigArrIndex<128;bigArrIndex++){
            for (int index = 0; index < 8; index++,index1+=3,index2+=3,index3+=3) {

                int[] arrOfEachPersonTypes = new int[8];
                arrOfEachPersonTypes[index] = assigmentGroupForPerson(arr1, index1, index2, index3);
                index++;
                arrOfEachPersonTypes[index] = assigmentGroupForPerson(arr2, index1, index2, index3);
                index++;
                arrOfEachPersonTypes[index] = assigmentGroupForPerson(arr3, index1, index2, index3);
                index++;
                arrOfEachPersonTypes[index] = assigmentGroupForPerson(arr4, index1, index2, index3);
                index++;
                arrOfEachPersonTypes[index] = assigmentGroupForPerson(arr5, index1, index2, index3);
                index++;
                arrOfEachPersonTypes[index] = assigmentGroupForPerson(arr6, index1, index2, index3);
                index++;
                arrOfEachPersonTypes[index] = assigmentGroupForPerson(arr7, index1, index2, index3);
                index++;
                arrOfEachPersonTypes[index] = assigmentGroupForPerson(arr8, index1, index2, index3);
                System.out.println(Arrays.toString(arrOfEachPersonTypes));

            }

        }
    }

    private static void printingOfGroupOfDifferentTypes(double[] arr1, double[] arr2, double[] arr3, double[] arr4) {
        System.out.print(Arrays.toString(calculatingOFGroupPersent(arr1)));
        System.out.println("max="+the_largest_num(calculatingOFGroupPersent(arr1)));
        System.out.print(Arrays.toString(calculatingOFGroupPersent(arr2)));
        System.out.println("max="+the_largest_num(calculatingOFGroupPersent(arr2)));
        System.out.print(Arrays.toString(calculatingOFGroupPersent(arr3)));
        System.out.println("max="+the_largest_num(calculatingOFGroupPersent(arr3)));
        System.out.print(Arrays.toString(calculatingOFGroupPersent(arr4)));
        System.out.println("max="+the_largest_num(calculatingOFGroupPersent(arr4)));
    }

    public static int the_largest_num(int... a) {
        Arrays.sort(a);
        return a[a.length-1];
    }

    static int[] calculatingOFGroupPersent (double [] arr){
        int index1 = 0;
        int index2 = 1;
        int index3 = 2;

        int type1 = 0;
        int type2 = 0;
        int type3 = 0;
        int type4 = 0;
        int type5 = 0;

        for (int index = 0; index <= 384; index++) {
            if (index1 < 384 && index2 < 384 && index3 < 384) {
                double t0 = assigmentOft0(arr, index1);//сюда передаем значения поочередно для каждого параметра с файла
                double t1 = assigmentOft1(arr, index2);
                double t3 = assigmentOft3(arr, index3);
                double h0 = calculating_h0(t1);//посчитали h0

                index1 += 3;
                index2 += 3;
                index3 += 3;

                switch (typeDefenition(t0, t1, t3, h0)) {
                    case (1):
                        type1++;
                        break;
                    case (2):
                        type2++;
                        break;
                    case (3):
                        type3++;
                        break;
                    case (4):
                        type4++;
                        break;
                    case (5):
                        type5++;
                        break;
                }

            } else {
                break;
            }
        }
        return new int[]{persentOfType(type1),persentOfType(type2),persentOfType(type3),persentOfType(type4),persentOfType(type5)};
    }

    static int assigmentGroupForPerson (double [] arr,int index1, int index2,int index3){

        double t0 = assigmentOft0(arr, index1);//сюда передаем значения поочередно для каждого параметра с файла
        double t1 = assigmentOft1(arr, index2);
        double t3 = assigmentOft3(arr, index3);
        double h0 = calculating_h0(t1);

        return (typeDefenition(t0, t1, t3, h0));
    }

    public static double assigmentOft0 (double arr[],int index){
        double t0 = arr[index];
        return t0;
    }

    public static double assigmentOft1 (double arr[],int index){
        double t1 = arr[index];
        return t1;
    }

    public static double assigmentOft3 (double arr[],int index){
        double t3 = arr[index];
        return t3;
    }

    public static double[] loadFromFile(File file) {
        new StringBuilder();
        double [] arr= new double[384];
        try (Scanner sc = new Scanner(file)) {
            for (int index=0; sc.hasNextLine();index++) {
               arr [index]= Double.parseDouble((String.valueOf(sc.nextLine())));
            }
        } catch (IOException e) {
            System.out.println(e);
        }
       // System.out.println(Arrays.toString(arr));
        return arr;
    }

    static double calculating_h0(double t1){
        double ho = (t1 / 100) * 10;
        return ho;
    }

    static boolean firstStep(double t0, double t1, double t3, double h0){
        double res= Math.abs(t0-t1);
        double res2 = Math.abs(t3-t1);
        boolean a;
        a = (res > h0) && (res2 > h0);
        return a;
    }

    static  boolean secondStepYes(double t0, double t1, double t3, double h0){
        double res= t1-t0;
        double res2 = t1-t3;
        boolean a;
        a = (res > h0) && (res2 > h0);
        return a;//+ maks -min
    }

    static  boolean secondStepNo(double t0, double t1, double t3, double h0) {
        double res= Math.abs(t1-t0);
        double res2 = Math.abs(t3-t1);
        boolean a;
        a = !(res < h0) && (res2 < h0);
        return a; //+ postiyno
    }

    static  boolean thirdStep(double t0, double t1, double t3, double h0) {
        double res= t1-t0;
        double res2 = t3-t1;
        boolean a;
        a = (res > h0) && (res2 > h0);
        return a; //+zrostanya
    }

    static  boolean fourthStep(double t0, double t1, double t3, double h0) {
        double res= t1-t0;
        double res2 = t3-t1;
        boolean a;
        a = (res < h0) && (res2 < h0);
        return a;//+ spadanya
    }

    static  boolean fifthStep(double t0, double t1, double t3, double h0) {

        double res = t3-t0;
        boolean a;
        a =  (res > h0);
        return a;//+ zrostanya - spadaniya
    }

    static int typeDefenition (double t0, double t1, double t3, double h0){
        if (firstStep( t0,t1,t3,h0)){
            if (secondStepYes(t0,t1,t3,h0)){
                return 1;//maks
            }
            else{
                return 2;//min
            }
        }
        else{
            if (secondStepNo(t0,t1,t3,h0)){
                return 3;//postiyna
            }
            else{
                if (thirdStep(t0,t1,t3,h0)){
                    return 4;//zrostanya
                }
                else{
                    if (fourthStep(t0,t1,t3,h0)){
                        return 5;//spadaniya
                    }
                    else{
                        if(fifthStep(t0,t1,t3,h0)){
                            return 4;//zrostanya
                        }
                        else{
                            return 5;//spadaniya
                        }
                    }
                }
            }
        }
    }

    static int persentOfType (int typeSum){
        int total = 128;
        return typeSum * 100 / total;
    }
}