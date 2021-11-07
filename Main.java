public class AppData {
    private String[] headers;
    private int[][] data;
    private Integer[][] data;

    public String[] getHeaders() {
        return headers;
    }

    public void setHeaders(String[] headers) {
        this.headers = headers;
    }

    public Integer[][] getData() {
        return data;
    }

    public void setData(Integer[][] data) {
        this.data = data;
    }
}
public class Homework_5 {
    public static void appendWrite(File file, String data) {
        try(OutputStream out = new FileOutputStream(file, true)) {
            out.write(data.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeToFileFromAppData(AppData appData, File file) {
        String[] headers = appData.getHeaders();
        if (headers == null) return;
        Integer[][] data = appData.getData();
        if (data == null) return;
        try(OutputStream out = new FileOutputStream(file)) {
            for (int i = 0; i < headers.length - 1; i++) {
                out.write((headers[i] + "; ").getBytes());
            }
            out.write((headers[headers.length - 1] + "\n").getBytes());
            for (Integer[] arr : data) {
                for (int i = 0; i < arr.length - 1; i++) {
                    out.write((arr[i] + "; ").getBytes());
                }
                out.write((arr[arr.length - 1] + "\n").getBytes());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readFromFileToAppData(AppData appData, File file) {
        String tokens[] = null;
        String currentLine;
        try(BufferedReader in = new BufferedReader(new FileReader(file))) {
            currentLine = in.readLine();
            tokens = currentLine.split("; ");
            appData.setHeaders(tokens);

            ArrayList<ArrayList <Integer>> data = new ArrayList<>();
            while (true) {
                ArrayList <Integer> tempInt = new ArrayList<>();
                currentLine = in.readLine();
                if (currentLine == null) break;
                tokens = currentLine.split("; ");
                for (String s : tokens) {
                    tempInt.add(Integer.parseInt(s));
                }
                data.add(tempInt);
            }
            Integer[][] arrData = data.stream().map(lst -> lst.toArray(new Integer[0])).toArray(Integer[][]::new);
            appData.setData(arrData);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        File file = new File("file.csv");
        appendWrite(file, "Val1; Val2; Val3; Val4\n");
        appendWrite(file, "1000; 2000; 3000; 4000\n");
        appendWrite(file, "4000; 5000; 6000; 7000\n");
        appendWrite(file, "7000; 8000; 9000; 1000\n");
        appendWrite(file, "1000; 1100; 1200; 1300\n");
        appendWrite(file, "1300; 1400; 1500; 1600\n");
        appendWrite(file, "1600; 1700; 1800; 1900\n");

        AppData appData = new AppData();
        readFromFileToAppData(appData, file);
        file.delete();
        File file2 = new File("file2.csv");
        writeToFileFromAppData(appData, file2);
    }

}
