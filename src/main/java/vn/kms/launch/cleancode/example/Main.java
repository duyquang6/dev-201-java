package vn.kms.launch.cleancode.example;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("Enter number of task 1,2,3,4,5, 6 or 7:");
        Scanner scan = new Scanner(System.in);
        int task = scan.nextInt();
        if(task == 1) {
            System.out.println("Have read all provided sources.");
        } else if (task == 2 || task == 3) {
            System.out.println("Create your own annotation. Create class with a few fields, some of which annotate with this annotation.");
            System.out.println("Though reflection print those fields in the class that were annotate by this annotation\n"+
            "Print annotation value into console");
            Class<UserAuthorization> guest = UserAuthorization.class;
            for (Field validateField : guest.getDeclaredFields()){
                Annotation annotation = validateField.getAnnotation(MyAnnotation.class);
                System.out.println("_______________________________________________");
                System.out.println("Fields in the class that were annotate by annotation:");
                System.out.println(validateField.getName());
                System.out.println("_______________________________________________");
                System.out.println("Annotation value:");
                System.out.println(annotation.toString());
                System.out.println("_______________________________________________");
            }
        }
        if(task == 4) {
            System.out.println("_______________________________________________");
            System.out.println("Invoke method (three method with different parameters and return types)");
            LinkedList<MyBook> books = new LinkedList<MyBook>();
            Class classBook = Class.forName("vn.kms.launch.cleancode.example.MyBook");
            Constructor c = classBook.getConstructor(String.class, String.class, Integer.class, Integer.class);
            MyBook book = (MyBook) c.newInstance("Danielle Steel", "Sisters", 474, 2000);
            books.add(book);
            book = (MyBook) c.newInstance("Jerome K Jerome", "Three men in a boat", 146, 1976);
            books.add(book);
            book = (MyBook) c.newInstance("O Henry", "100 Selected Stories", 142, 1997);
            books.add(book);

            Method m1 = classBook.getDeclaredMethod("showInfo");
            m1.invoke(book);
            System.out.println("How many years ago books were published:");
            for (MyBook bookItem : books){
                Method m2 = classBook.getDeclaredMethod("countOfYear", Integer.class);
                Integer countOfYear = (Integer) m2.invoke(bookItem,bookItem.getPublishingYear());
                System.out.println(bookItem.getBookName() + " - " + countOfYear);
            }
            System.out.println("_______________________________________________");
            System.out.println("Books of author Danielle Steel:");
            Method m3 = classBook.getMethod("booksOfMyFavouriteAuthor", String.class, List.class);
            List<MyBook> favouriteBooks = (List<MyBook>) m3.invoke(book, "Danielle Steel", books);
            for (MyBook item : favouriteBooks) {
                System.out.println(item.getBookName());
            }
            System.out.println("_______________________________________________");
        }
        if (task == 5) {
            System.out.println("Set value into field not knowing its type");
            GenericClass<String> genericObj = new GenericClass<>();
            System.out.println("_______________________________________________");
            System.out.println("Field Type before is: " + genericObj.getClass().getDeclaredField("genericField").getGenericType().getTypeName());
            System.out.println("Field value before is: "+String.valueOf(genericObj.getGenericField()));
            System.out.println("_______________________________________________");
            Field genericField = null;
            try {
                genericField = genericObj.getClass().getDeclaredField("genericField");
            } catch (NoSuchFieldException nsfException) {
                System.out.println(nsfException.getMessage());
            }

            if (genericField != null) {
                genericField.setAccessible(true);
                genericField.set(genericObj, "ThisIsMyString");
            }

            System.out.println("Field Type after is: "+genericObj.getGenericField().getClass());
            System.out.println("Field value after is: "+String.valueOf(genericObj.getGenericField()));
            System.out.println("_______________________________________________");
        }
        if (task == 6) {
            System.out.println("_______________________________________________");
            System.out.println("Invoke myMethod(String a, int ...args):");
            Class<?> clazz = Class.forName("vn.kms.launch.cleancode.example.MyMultipleParameters");
            MyMultipleParameters myObj = new MyMultipleParameters();
            Method myMethod1 = clazz.getMethod("myMethod", String.class, int[].class);
            int[] array = new int[]{1, 2, 3, 4, 5};
            System.out.println("RESULT: "+myMethod1.invoke(myObj,"The sum of elements is:",array));
            System.out.println("_______________________________________________");
            System.out.println("");
            System.out.println("_______________________________________________");
            System.out.println("Invoke myMethod(String args)");
            Method myMethod2 = clazz.getMethod("myMethod", String[].class);
            System.out.println("RESULT: "+myMethod2.invoke(myObj,new Object[]{new String[]{"1","2","3","4","5"}}));
            System.out.println("_______________________________________________");
        }
        if (task == 7){
            System.out.println("Show all information about that class that receives object of unknown type: ");
            ClassMetaInformation<BigDecimal> classMetaInformation = new ClassMetaInformation<>("secret", BigDecimal.TEN);
            Class<? extends ClassMetaInformation> metaInformationClass = classMetaInformation.getClass();
            System.out.println("_______________________________________________");
            System.out.println("Class data example: " + metaInformationClass.getSimpleName());
            System.out.println("SuperClass Info: " + metaInformationClass.getSuperclass().getSimpleName());
            System.out.println("Debug info: " + metaInformationClass.getName());
            System.out.println("_______________________________________________");
            System.out.println("Fields: ");
            for (Field field : metaInformationClass.getDeclaredFields()) {
                System.out.println(getModifierName(field.getModifiers()) + " " +
                        field.getType().getSimpleName() + " " +
                        field.getName() + ";");
            }
            System.out.println("Methods: ");
            for (Method method : metaInformationClass.getDeclaredMethods()) {
                System.out.println(getModifierName(method.getModifiers()) + " " + method.getName() +
                        "(" + Arrays.toString(method.getParameters()) + ")");
            }
            System.out.println("Superclass: ");
            System.out.println(metaInformationClass.getSuperclass().getName());

            System.out.println("Package: ");
            System.out.println(metaInformationClass.getPackage());
        }
    }

    private static String getModifierName(int mod) {
        String result = "default";
        if (Modifier.isPublic(mod)) {
            result = "public";
        } else if (Modifier.isPrivate(mod)) {
            result = "private";
        } else if (Modifier.isProtected(mod)) {
            result = "protected";
        }

        return result;
    }
}
