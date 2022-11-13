import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;

public class contactForm extends JFrame {
    public contactForm() {
    }

    public static void restartApplication() {
        String var10000 = System.getProperty("java.home");
        String javaBin = var10000 + File.separator + "bin" + File.separator + "java";
        File currentJar = new File("C:\\Users\\Admin\\Desktop\\AnketaUpProject.jar");
        if (currentJar.getName().endsWith(".jar")) {
            ArrayList<String> command = new ArrayList();
            command.add(javaBin);
            command.add("-jar");
            command.add(currentJar.getPath());
            ProcessBuilder builder = new ProcessBuilder(command);

            try {
                builder.start();
            } catch (IOException var6) {
                throw new RuntimeException(var6);
            }

            System.exit(0);
        }

    }

    static class CONT extends JFrame {
        JTextField name_field;
        JTextField number_field;

        public CONT() {
            super("Анкета");
            super.setBounds(250, 250, 600, 200);
            super.setDefaultCloseOperation(3);
            Container container = super.getContentPane();
            container.setLayout(new GridLayout(5, 2, 2, 10));
            JLabel name = new JLabel("ФИО");
            this.name_field = new JTextField();
            JLabel number = new JLabel("Номер телефона: ");
            this.number_field = new JTextField();
            container.add(name);
            container.add(this.name_field);
            container.add(number);
            container.add(this.number_field);
            JButton send = new JButton("Добавить контакт");
            container.add(send);
            String[] nmbArray = new String[0];
            final ArrayList<String> numberandnames = new ArrayList(Arrays.asList(nmbArray));
            String[] nmbArray1 = new String[0];
            final ArrayList<String> numberandnames1 = new ArrayList(Arrays.asList(nmbArray1));
            send.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    numberandnames.add(CONT.this.name_field.getText());
                    numberandnames1.add(CONT.this.number_field.getText());

                    try {
                        File file = new File("C:\\Users\\Admin\\Desktop\\Anketa.docx");
                        file.createNewFile();
                        Path link = Paths.get("C:\\Users\\Admin\\Desktop\\Anketa.docx");
                        if (Files.exists(link, new LinkOption[0])) {
                            FileInputStream fis = new FileInputStream(file.getAbsolutePath());
                            XWPFDocument docxModel = new XWPFDocument(fis);
                            docxModel.createParagraph();
                            String documentLine = docxModel.getDocument().toString();
                            CTSectPr ctSectPr = docxModel.getDocument().getBody().addNewSectPr();
                            XWPFParagraph bodyParagraph = docxModel.createParagraph();
                            bodyParagraph.setAlignment(ParagraphAlignment.LEFT);
                            XWPFRun paragraphConfig = bodyParagraph.createRun();
                            XWPFParagraph paragraph = docxModel.createParagraph();
                            paragraphConfig.setItalic(true);
                            paragraphConfig.setFontSize(20);
                            paragraphConfig.setColor("170101");
                            paragraphConfig.setFontSize(12);
                            List<XWPFParagraph> paragraphs = docxModel.getParagraphs();
                            String word = numberandnames.toString();
                            int k = 0;
                            String[] data = word.split("");
                            String word1 = numberandnames1.toString();
                            String [] data1 = word1.split("");
                            Word wrd = new Word();
                            Warning warning = new Warning();

                            for(int i = 0; i < data.length; ++i) {
                                if (data[i].equals(" ")) {
                                    ++k;
                                }
                            }

                            int maxlenght = 12;
                            int count = 0;
                            for (int n = 0; n < number_field.getText().length(); n++) {
                                if (Character.isDigit(number_field.getText().charAt(n))) {
                                    count++;
                                }
                            }

                            for(int i = 0; i < data1.length; ++i) {
                                if (data1[i].equals("+")) {
                                    count++;
                                }
                            }


                            if (k >= 1 & k <= 2) {
                                Iterator var22x = paragraphs.iterator();
                                if (var22x.hasNext()) {
                                    if (count == maxlenght) {
                                        DatabaseHandler databaseHandler = new DatabaseHandler();
                                        databaseHandler.signUpUser(CONT.this.name_field.getText(), CONT.this.number_field.getText());
                                        XWPFParagraph para = (XWPFParagraph) var22x.next();
                                        paragraphConfig.setText(numberandnames.toString());
                                        paragraphConfig.setText(numberandnames1.toString());
                                        wrd.setVisible(true);
                                    }
                                }
                            }

                            if(count < maxlenght & count > maxlenght){
                                wrd.dispose();
                                warning.setVisible(true);
                                return;
                            }

                            if (k >= 3) {
                                wrd.dispose();
                                warning.setVisible(true);
                                return;
                            }

                            fis.close();

                            try {
                                FileOutputStream outputStream = new FileOutputStream("C:\\Users\\Admin\\Desktop\\Anketa.docx");
                                docxModel.write(outputStream);
                                outputStream.close();
                            } catch (Exception var21) {
                                throw new RuntimeException(var21);
                            }
                        }

                    } catch (IOException var22) {
                        throw new RuntimeException(var22);
                    }
                }
            });
        }
    }

    static class Word extends JFrame {
        public Word() {
            super("Анкета");
            this.setDefaultCloseOperation(3);
            JButton docx = new JButton(".docx");
            docx.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Desktop desktop = null;
                    if (Desktop.isDesktopSupported()) {
                        desktop = Desktop.getDesktop();

                        try {
                            desktop.open(new File("C:\\Users\\Admin\\Desktop\\Anketa.docx"));
                        } catch (IOException var4) {
                            var4.printStackTrace();
                        }
                    }

                    Word.this.dispose();
                }
            });
            JPanel contents = new JPanel();
            contents.add(docx);
            this.setContentPane(contents);
            this.setSize(350, 100);
        }
    }

    static class Warning extends JFrame {
        public Warning() {
            super.setBounds(400, 290, 300, 100);
            super.setDefaultCloseOperation(2);
            Container container = super.getContentPane();
            JLabel wrg1 = new JLabel("     Неверный ввод данных!     ", 0);
            wrg1.setForeground(new Color(225, 64, 64));
            wrg1.setFont(new Font("Verdana", 0, 15));
            container.add(wrg1);
            new CONT();
            JButton warn = new JButton("ОК");
            warn.setPreferredSize(new Dimension(30, 30));
            container.add(warn, "South");
            warn.addActionListener((e) -> {
                contactForm.restartApplication();
            });
        }
    }
}

