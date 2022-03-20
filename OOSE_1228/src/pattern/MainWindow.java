package pattern;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import command.CopyCommand;
import command.CutCommand;
import command.PasteCommand;
import command.Receiver;
import Inteprete.*;
import model.Icon;
import model.Menu;
import model.MenuBar;
import model.MenuItem;
import model.*;
import parse.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.font.TextAttribute;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;


public class MainWindow extends Window {
    // Frame
    private final JFrame frame;
    private final int width = 1000;
    private final int height = 500;
    private final DialogWindow dialogWindow;

    // Handler初始化
    private final WidgetFactory widgetFactory;

    // 編輯區初始化
    private final JTextPane editorViewer = new JTextPane();
    private final JPanel statusBar = new JPanel(new BorderLayout());
    private final JLabel statusLabel = new JLabel();

    // 排版物件及Visitor物件初始化
    private Formatting formatting = new FullFormatting();

    // Glyph初始化
    private Glyph root = null;

    // MenuBar物件宣告
    private Menu fileMenu;
    private MenuItem newFileMenu;
    private MenuItem openFileMenu;
    private MenuItem saveFileMenu;
    private Menu editMenu;
    private MenuItem cutEditMenu;
    private MenuItem copyEditMenu;
    private MenuItem pasteEditMenu;
    private MenuItem insertImgEditMenu;
    private Menu fontStyleMenu;
    private MenuItem boldFontStyleMenu;
    private MenuItem italicFontStyleMenu;
    private MenuItem underlineFontStyleMenu;
    private Menu colorMenu;
    private MenuItem redBackgroundColorMenu;
    private MenuItem greenBackgroundColorMenu;
    private MenuItem blueBackgroundColorMenu;
    private MenuItem blackBackgroundColorMenu;
    private MenuItem noneBackgroundColorMenu;
    private MenuItem redForegroundColorMenu;
    private MenuItem greenForegroundColorMenu;
    private MenuItem blueForegroundColorMenu;
    private MenuItem whiteForegroundColorMenu;
    private MenuItem blackForegroundColorMenu;
    private Menu formatMenu;
    private MenuItem fullFormatMenu;
    private MenuItem plaintextFormatMenu;
    private Menu helpMenu;
    private MenuItem aboutHelpMenu;
    private Menu InserMenu;
    private MenuItem Icon100;
    private MenuItem Icon200;
    private MenuItem Icon300;
    private MenuItem Icon400;
    private MenuItem Icon500;
    private JComboBox jComboBox,jComboBox1;
    private JPanel panel;
    private boolean active = true;
    private RecordEdit recordEdit;
    private Calculator calculater=new Calculator();
    private JOptionPane jOptionPane;


    private  DocumentListener documentListener=new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {if(active) loadFromEditorViewer(); }
        @Override
        public void removeUpdate(DocumentEvent e) { if(active) loadFromEditorViewer(); }
        @Override
        public void changedUpdate(DocumentEvent e) { if(active) loadFromEditorViewer(); }
    };


    private ActionListener actionListener=new ActionListener() {
        Glyph root;
        Receiver receiver = new Receiver();
        CutCommand cutCommand = new CutCommand(receiver);
        CopyCommand copyCommand = new CopyCommand(receiver);
        PasteCommand pasteCommand = new PasteCommand(receiver);
        ProtoMgr protoMgr = ProtoMgr.getInstance();
        int imageQuantity = 0;

        @Override
        public void actionPerformed(ActionEvent e) {
            String s = e.getActionCommand();
            int index=jComboBox.getSelectedIndex();
            int index1=jComboBox1.getSelectedIndex();
            this.root = getRoot();
            Iterator iterator=root.getIterator();
            System.out.println(iterator.next().getContent());

            switch (index){
                case 1:
                    System.out.println(e);
                    new BackgroundColorAction("BG-Red", Color.RED).actionPerformed(e);
                    break;
                case 2:
                    new BackgroundColorAction("BG-Green",Color.green).actionPerformed(e);
                    break;
                case 3:
                    new BackgroundColorAction("BG-Blue", Color.BLUE).actionPerformed(e);
                    break;
                default:
                    new BackgroundColorAction("BG-Blue", null).actionPerformed(e);
                    break;
            }

            switch (index1){
                case 1:
                    new ForegroundColorAction("white",Color.white).actionPerformed(e);
                    break;
                case 3:
                    new ForegroundColorAction("red",Color.red).actionPerformed(e);
                    break;
                default:
                    new ForegroundColorAction("black",Color.BLACK).actionPerformed(e);
                    break;
            }

            switch (s) {
                //----------------------------------------編輯----------------------------------------
                case "cut":
                    System.out.println("cut");
                    cutCommand.execute(e);
                    break;
                case "copy":
                    System.out.println("copy");
                    copyCommand.execute(e);
                    break;
                case "paste":
                    System.out.println("paste");
                    pasteCommand.execute(e);
                    break;
                case "undo":
                    System.out.println("undo");
                    recordEdit.restore();
                    break;
                case "insertImg": {
                    // 建立檔案選擇器
                    JFileChooser jFileChooser = new JFileChooser("f:");
                    jFileChooser.setFileFilter(new FileNameExtensionFilter("jpg, jpeg, png, and gif", new String[] {"JPEG", "JPG", "PNG", "GIF"}));
                    setFileChooserText("插入圖片", "插入");
                    SwingUtilities.updateComponentTreeUI(jFileChooser);
                    // 若使用者選擇檔案
                    if (jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                        // 將標籤設置為所選目錄的路徑
                        String absPath = jFileChooser.getSelectedFile().getAbsolutePath();
                        AttributeSet outerAttr = new InsertImageAction(absPath,"insertImg").actionPerformed(e);
                        insertEditorImage(outerAttr);
                    } else {
                        showDialog("取消開啟舊檔操作", "提醒");
                    }
                    break;
                }

                //----------------------------------------樣式----------------------------------------
                case "Bold":
                    new StyledEditorKit.BoldAction().actionPerformed(e);
                    break;
                case "Italic":
                    new StyledEditorKit.ItalicAction().actionPerformed(e);
                    break;
                case "Underline":
                    new StyledEditorKit.UnderlineAction().actionPerformed(e);
                    break;

                //----------------------------------------色彩----------------------------------------
                case "BG-re":
                    System.out.println(e);
                    new BackgroundColorAction("BG-Red", Color.RED).actionPerformed(e);
                    break;
                case "BG-Green":
                    new BackgroundColorAction("BG-Green", Color.GREEN).actionPerformed(e);
                    break;
                case "BG-Blue":
                    new BackgroundColorAction("BG-Blue", Color.BLUE).actionPerformed(e);
                    break;
                case "BG-Black":
                    new BackgroundColorAction("BG-Black", Color.BLACK).actionPerformed(e);
                    break;
                case "BG-None":
                    new BackgroundColorAction("BG-None", null).actionPerformed(e);
                    break;
                case "FG-Red":
                    new ForegroundColorAction("FG-Red", Color.RED).actionPerformed(e);
                    break;
                case "FG-Green":
                    new ForegroundColorAction("FG-Green", Color.GREEN).actionPerformed(e);
                    break;
                case "FG-Blue":
                    new ForegroundColorAction("FG-Blue", Color.BLUE).actionPerformed(e);
                    break;
                case "FG-White":
                    new ForegroundColorAction("FG-White", Color.WHITE).actionPerformed(e);
                    break;
                case "FG-Black":
                    new ForegroundColorAction("FG-Black", Color.BLACK).actionPerformed(e);
                    break;

                //----------------------------------------檔案----------------------------------------
                case "new":
                    root = new Root();
                    root.insert(new Paragraph());
                    setEditorContent(root);
                    break;
                case "open": {
                    // 建立檔案選擇器
                    JFileChooser jFileChooser = new JFileChooser("f:");
                    jFileChooser.setFileFilter(new FileNameExtensionFilter("OOSE Document File", new String[] {"OOSEFILE"}));
                    setFileChooserText("開啟舊檔", "開啟");
                    SwingUtilities.updateComponentTreeUI(jFileChooser);
                    // 若使用者選擇檔案
                    if (jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                        // 將絕對路徑設到已選的目錄
                        File file = new File(jFileChooser.getSelectedFile().getAbsolutePath().replaceAll("\\.oosefile", "") + ".oosefile");

                        try {
                            // 建立 file reader 及 buffered reader
                            InputStreamReader fileReader = new InputStreamReader (new FileInputStream(file), StandardCharsets.UTF_8);    //FileReader fileReader = new FileReader(file);
                            BufferedReader bufferedReader = new BufferedReader(fileReader);
                            // 一行一行讀入
                            String opening_oosefile = "";
                            String readline = "";
                            opening_oosefile = bufferedReader.readLine();
                            while ((readline = bufferedReader.readLine()) != null)
                                opening_oosefile = opening_oosefile + "\n" + readline;

                            // 設定到editorViewer
                            root = new GlyphParser().parse(opening_oosefile);
                            setEditorContent(root);
                            // 設定排版
                            Map<String, Object> document = new Gson().fromJson(opening_oosefile, new TypeToken<Map<String, Object>>(){}.getType());
                            if(document.get("format").equals("Full")) setFormatting(new FullFormatting());
                            else  setFormatting(new PlaintextFormatting());
                        } catch (Exception evt) {
                            System.out.println(evt.getMessage());
                            showDialog(evt.getMessage(), "錯誤");
                        }

                    }
                    // If the user cancelled the operation
                    else
                        showDialog("取消儲存檔案操作", "提醒");
                    break;

                }
                case "save": {
                    // 建立檔案選擇器
                    JFileChooser jFileChooser = new JFileChooser("f:");
                    jFileChooser.setFileFilter(new FileNameExtensionFilter("OOSE Document File", new String[] {"OOSEFILE"}));
                    setFileChooserText("儲存檔案", "儲存");
                    SwingUtilities.updateComponentTreeUI(jFileChooser);
                    // 若使用者選擇檔案
                    if (jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                        // 將絕對路徑設到已選的目錄
                        File file = new File(jFileChooser.getSelectedFile().getAbsolutePath().replaceAll("\\.oosefile", "") + ".oosefile");

                        try {
                            // 建立 file writer 及 buffered writer
                            OutputStreamWriter fileWriter = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);    //FileWriter fileWriter = new FileWriter(file, false);
                            BufferedWriter writer = new BufferedWriter(fileWriter);
                            // 寫入資料
                            GlyphToOOSEFILEVisitor glyphToOOSEFILEVisitor = new GlyphToOOSEFILEVisitor();
                            root.accept(glyphToOOSEFILEVisitor);
                            String saving_oosefile = "{\"format\": \"" + getFormatting().getTYPE() + "\"," +
                                    "\"content\": " + glyphToOOSEFILEVisitor.getParseString() + "}";
                            writer.write(saving_oosefile);
                            writer.flush();
                            writer.close();
                        } catch (Exception evt) {
                            evt.printStackTrace();
                            showDialog(evt.getMessage(), "錯誤");
                        }
                    }
                    // If the user cancelled the operation
                    else
                        showDialog("取消儲存檔案操作", "提醒");
                    break;
                }




                //----------------------------------------排版----------------------------------------
                //按下的按鈕 為 "完整"
                case "full":
                    setFormatting(new FullFormatting());
                    panel.setVisible(true);
                    break;
                //按下的按鈕 為 "純文字"
                case "plaintext":
                    setFormatting(new PlaintextFormatting());
                    panel.setVisible(false);
                    break;

                //----------------------------------------幫助----------------------------------------
                case "about":
                    showDialog("這是運行在" + getEnvironment() + "環境的文件編輯器", "關於");
                    break;

                //-------------------------------------插入不同大小的圖片------------------------------------
                case "Icon100":
                    Prototype prototype=protoMgr.getIcon("100");
                    Icon icon=(Icon) prototype.clone();
                    icon.setClassName("Icon"+(++imageQuantity));
                    AttributeSet outerAttr = new InsertImageAction(icon.getPath(),icon.getClassName()).actionPerformed(e);
                    insertEditorImage(outerAttr);
                    System.out.println(icon.getPath());
                    System.out.println(icon.getClassName());
                    System.out.println(editorViewer.getText());
                    break;

                case "Icon200":
                    prototype=protoMgr.getIcon("200");
                    icon=(Icon) prototype.clone();
                    icon.setClassName("Icon"+(++imageQuantity));
                    outerAttr = new InsertImageAction(icon.getPath(),icon.getClassName()).actionPerformed(e);
                    insertEditorImage(outerAttr);
                    System.out.println(icon.getPath());
                    System.out.println(icon.getClassName());
                    System.out.println(editorViewer.getText());
                    break;

                case "Icon300":
                    prototype=protoMgr.getIcon("300");
                    icon=(Icon) prototype.clone();
                    icon.setClassName("Icon"+(++imageQuantity));
                    outerAttr = new InsertImageAction(icon.getPath(),icon.getClassName()).actionPerformed(e);
                    insertEditorImage(outerAttr);
                    System.out.println(icon.getPath());
                    System.out.println(icon.getClassName());
                    System.out.println(editorViewer.getText());
                    break;

                case "Icon400":
                    prototype=protoMgr.getIcon("400");
                    icon=(Icon) prototype.clone();
                    icon.setClassName("Icon"+(++imageQuantity));
                    outerAttr = new InsertImageAction(icon.getPath(),icon.getClassName()).actionPerformed(e);
                    insertEditorImage(outerAttr);
                    System.out.println(icon.getPath());
                    System.out.println(icon.getClassName());
                    System.out.println(editorViewer.getText());
                    break;

                case "Icon500":
                    prototype=protoMgr.getIcon("500");
                    icon=(Icon) prototype.clone();
                    icon.setClassName("Icon"+(++imageQuantity));
                    outerAttr = new InsertImageAction(icon.getPath(),icon.getClassName()).actionPerformed(e);
                    insertEditorImage(outerAttr);
                    System.out.println(icon.getPath());
                    System.out.println(icon.getClassName());
                    System.out.println(editorViewer.getText());
                    break;
            }


        }

        public void setFileChooserText(String openDialogTitleText, String openButton){
            UIManager.put("FileChooser.openDialogTitleText", openDialogTitleText);
            UIManager.put("FileChooser.lookInLabelText", "目前");
            UIManager.put("FileChooser.openButtonText", openButton);
            UIManager.put("FileChooser.cancelButtonText", "取消");
            UIManager.put("FileChooser.fileNameLabelText", "檔案名稱");
            UIManager.put("FileChooser.filesOfTypeLabelText", "檔案類型");
            UIManager.put("FileChooser.openButtonToolTipText", openButton+"指定的檔案");
            UIManager.put("FileChooser.cancelButtonToolTipText","取消");
        }
    };


    //================================================== GUI ==================================================
    public MainWindow(WindowImp impl) {

        super(impl);                                //把Implementor存著
        recordEdit = new RecordEdit(editorViewer);
        editorViewer.setName("first");

        editorViewer.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                recordEdit.save();
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        widgetFactory = super.getWidgetFactory();   //依不同的Implementor取得WidgetFactory

        //-------------------- JFrame --------------------
        // 建立JFrame
        frame = super.drawFrame();                  //依不同的Implementor建出Frame

        frame.setIconImage(new ImageIcon("text-gf238f7d9b_1280.png").getImage());
        frame.setTitle("Document Editor");
        frame.setBounds(100, 100, width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 建立MenuBar
        frame.setJMenuBar(createMenuBar());


        //建立JRadioButton
        panel=new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(Color.darkGray);
        JRadioButton rb1=new JRadioButton("Bold");    //创建JRadioButton对象
        rb1.setActionCommand("Bold");
        JRadioButton rb2=new JRadioButton("Italic");
        rb2.setActionCommand("Italic");
        JRadioButton rb3=new JRadioButton("Underline");//创建JRadioButton对象
        rb3.setActionCommand("Underline");
        ButtonGroup group=new ButtonGroup();
        group.add(rb1);
        group.add(rb2);
        group.add(rb3);
        panel.add(rb1);
        panel.add(rb2);
        panel.add(rb3);
        //設置監聽對像
        rb1.addActionListener(actionListener);
        rb2.addActionListener(actionListener);
        rb3.addActionListener(actionListener);

        //建立BC選單
        jComboBox=new JComboBox();
        jComboBox.addItem("---BackgroundColor---");    //向下拉列表中添加一项
        jComboBox.addItem("red");
        jComboBox.addItem("green");
        jComboBox.addItem("blue");
        jComboBox.setSelectedIndex(0);
        jComboBox.addActionListener(actionListener);
        panel.add(jComboBox);

        //建立FC選單
        jComboBox1=new JComboBox();
        jComboBox1.addItem("---ForegroundColor---");    //向下拉列表中添加一项
        jComboBox1.addItem("white");
        jComboBox1.addItem("black");
        jComboBox1.addItem("red");
        jComboBox1.setSelectedIndex(0);
        jComboBox1.addActionListener(actionListener);
        panel.add(jComboBox1);

        JButton jButton=new JButton();
        jButton.setText("undo");
        jButton.setActionCommand("undo");
        jButton.addActionListener(actionListener);
        panel.add(jButton);

        // 設定Layout
        frame.setLayout(new BorderLayout());
        frame.add(createScrollPane(editorViewer), BorderLayout.CENTER);
        frame.add(statusBar, BorderLayout.SOUTH);
        frame.add(panel,BorderLayout.NORTH);
        // 設定 Dialog Window
        dialogWindow = new DialogWindow(impl);

        //-------------------- 狀態欄 --------------------
        statusLabel.setHorizontalAlignment(JLabel.RIGHT);
        statusLabel.setFont(super.getSystemFont());
        statusBar.setPreferredSize(new Dimension(statusBar.getWidth(), 30));
        statusBar.add(statusLabel);
        //-------------------- 編輯器區 --------------------
        editorViewer.setContentType("text/html");
        editorViewer.setText("<html><head></head><body><p></p></body></html>");
        editorViewer.getDocument().addDocumentListener(documentListener);
        editorViewer.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e) { insertBlankText(); }
        });


    }



    // 執行
    public boolean run(){
        //打包frame並顯示
        frame.pack();
        frame.setSize(width, height);
        frame.setVisible(true);
        loadFromEditorViewer();
        return true;
    }


    //================================================== 繪製 ==================================================
    // 繪製 Glyph 內容到 editorViewer 上
    public void drawIntoEditorViewer(){
        if(getActive()) {
            Runnable doAssist = () -> {
                // 暫停監聽
                setActive(false);
                // 儲存游標狀態
                int caretPosition = editorViewer.getCaretPosition();
                // 執行Visit
                DrawGlyphVisitor drawGlyphVisitor = new DrawGlyphVisitor(formatting);
                CountCharacterVisitor countCharacterVisitor = new CountCharacterVisitor();
                root.accept(drawGlyphVisitor);      //傳入drawGlyphVisitor處理Glyph解析
                root.accept(countCharacterVisitor); //利用countCharacterVisitor計算字數
                // 畫到 editorViewer上
                editorViewer.setText("<html><head></head>" + drawGlyphVisitor.getParseString() + "</html>");
                editorViewer.setCaretPosition(Math.min(caretPosition, editorViewer.getDocument().getLength()-1));
                System.out.println(drawGlyphVisitor.getResult());
                System.out.println(buildSytaxTree(drawGlyphVisitor.getResult()));
                // 顯示目前狀態
                statusLabel.setText( countCharacterVisitor.getParagraph() + " 個段落　" + countCharacterVisitor.getCharacter() + " 個字元　|　排版模式：" + formatting.getTYPE() + "　　");
                // 重新監聽
                setActive(true);
            };
            SwingUtilities.invokeLater(doAssist);
        }
    }
    public void setActive(boolean active) { this.active = active; }
    public boolean getActive() { return this.active; }
    // 讀取使用者輸入的字
    public void loadFromEditorViewer(){ setEditorContent(new HTMLtoGlyphParser().parse(editorViewer.getText())); }


    //================================================== GETTER SETTER ==================================================
    // 設定內容到 editorViewer
    public void setEditorContent(Glyph root){
        this.root = root;
        drawIntoEditorViewer();
    }
    // 設定排版
    public void setFormatting(Formatting formatting){
        // 設定排版模式
        this.formatting = formatting;
        drawIntoEditorViewer();
        // 選單按鈕調整
        boolean enabled = this.formatting.getTYPE().equals("Full");
        insertImgEditMenu.setEnabled(enabled);
        fontStyleMenu.setEnabled(enabled);
        colorMenu.setEnabled(enabled);
    }
    // 取得排版
    public Formatting getFormatting(){ return this.formatting; }
    // 取得Glyph
    public Glyph getRoot() { return this.root; }
    // 啟動Dialog Window
    public void showDialog(String message, String title) {
        dialogWindow.showDialog(message, title);
    }


    //================================================== INSERT ACTION ==================================================
    // 插入圖片
    public void insertEditorImage(AttributeSet outerAttr){
        try { editorViewer.getDocument().insertString(editorViewer.getCaretPosition()," ", outerAttr); }
        catch (BadLocationException e) { e.printStackTrace(); }
    }
    // 插入空白字元
    public void insertBlankText(){
        try {
            editorViewer.getDocument().insertString(editorViewer.getCaretPosition(),"&nbsp;", null);
            editorViewer.setCaretPosition(Math.max(0, editorViewer.getCaretPosition()-6));
        }
        catch (BadLocationException e) { e.printStackTrace(); }
    }


    // ==================================================建立 ScrollPane==================================================
    public JScrollPane createScrollPane(Component component){
        JScrollPane scrollPane = new JScrollPane();
        // 建立並設定 ScrollBar
        scrollPane.setVerticalScrollBar(widgetFactory.createScrollbar().setOriented(JScrollBar.VERTICAL));
        scrollPane.setHorizontalScrollBar(widgetFactory.createScrollbar().setOriented(JScrollBar.HORIZONTAL));
        // 內容放入 ScrollPane
        scrollPane.setViewportView(component);
        scrollPane.setBorder(null);
        return scrollPane;
    }

    // ==================================================建立選單列==================================================
    public MenuBar createMenuBar() {
        MenuBar bar = widgetFactory.createMenuBar();

        // --------------------------------------------------建立選單選項 [檔案]--------------------------------------------------
        fileMenu = widgetFactory.createMenu();
        fileMenu.setDescription("File");
        // 建立 [檔案] 選單子選項
        // --------------------
        newFileMenu =  widgetFactory.createMenuItem();
        newFileMenu.setDescription("new");
        newFileMenu.setActionCommand("new");
        // --------------------
        openFileMenu = widgetFactory.createMenuItem();
        openFileMenu.setDescription("previous");
        openFileMenu.setActionCommand("open");
        // --------------------
        saveFileMenu = widgetFactory.createMenuItem();
        saveFileMenu.setDescription("save");
        saveFileMenu.setActionCommand("save");
        // --------------------

        // 監聽指定選項
        newFileMenu.addActionListener(actionListener);
        openFileMenu.addActionListener(actionListener);
        saveFileMenu.addActionListener(actionListener);

        // 按鈕加入上級選單中
        fileMenu.addMenuItem(newFileMenu);
        fileMenu.addMenuItem(openFileMenu);
        fileMenu.addMenuItem(saveFileMenu);

        // --------------------------------------------------建立選單選項 [編輯]--------------------------------------------------
        editMenu = widgetFactory.createMenu();
        editMenu.setDescription("Edit");
        // 建立 [編輯] 選單子選項
        // --------------------
        cutEditMenu = widgetFactory.createMenuItem();
        cutEditMenu.setDescription("Cut");
        cutEditMenu.setActionCommand("cut");
        // --------------------
        copyEditMenu = widgetFactory.createMenuItem();
        copyEditMenu.setDescription("Copy");
        copyEditMenu.setActionCommand("copy");
        // --------------------
        pasteEditMenu = widgetFactory.createMenuItem();
        pasteEditMenu.setDescription("Paste");
        pasteEditMenu.setActionCommand("paste");
        // --------------------
        insertImgEditMenu = widgetFactory.createMenuItem();
        insertImgEditMenu.setDescription("Insert Img");
        insertImgEditMenu.setActionCommand("insertImg");

        // 監聽指定選項
        cutEditMenu.addActionListener(actionListener);
        copyEditMenu.addActionListener(actionListener);
        pasteEditMenu.addActionListener(actionListener);
        insertImgEditMenu.addActionListener(actionListener);

        // 按鈕加入上級選單中
        editMenu.addMenuItem(cutEditMenu);
        editMenu.addMenuItem(copyEditMenu);
        editMenu.addMenuItem(pasteEditMenu);
        editMenu.addMenuItem(insertImgEditMenu);

        // --------------------------------------------------建立選單選項 [樣式]--------------------------------------------------
        fontStyleMenu = widgetFactory.createMenu();
        fontStyleMenu.setDescription("Style");
        // 建立 [樣式] 選單子選項
        // --------------------
        boldFontStyleMenu = widgetFactory.createMenuItem();
        boldFontStyleMenu.setDescription("Bold");
        boldFontStyleMenu.setActionCommand("Bold");
        // --------------------
        italicFontStyleMenu = widgetFactory.createMenuItem();
        italicFontStyleMenu.setDescription("Italic");
        italicFontStyleMenu.setActionCommand("Italic");
        // --------------------
        underlineFontStyleMenu = widgetFactory.createMenuItem();
        underlineFontStyleMenu.setDescription("Underline");
        underlineFontStyleMenu.setActionCommand("Underline");
        Map attributes = underlineFontStyleMenu.getFont().getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);


        // 監聽指定選項
        boldFontStyleMenu.addActionListener(actionListener);
        italicFontStyleMenu.addActionListener(actionListener);
        underlineFontStyleMenu.addActionListener(actionListener);

        // 按鈕加入上級選單中
        fontStyleMenu.addMenuItem(boldFontStyleMenu);
        fontStyleMenu.addMenuItem(italicFontStyleMenu);
        fontStyleMenu.addMenuItem(underlineFontStyleMenu);

        // --------------------------------------------------建立選單選項 [色彩]--------------------------------------------------
        colorMenu = widgetFactory.createMenu();
        colorMenu.setDescription("BGcolor");
        // 建立 [色彩] 選單子選項
        // --------------------
        redBackgroundColorMenu = widgetFactory.createMenuItem();
        redBackgroundColorMenu.setDescription("Red");
        redBackgroundColorMenu.setActionCommand("BG-re");
        // --------------------
        greenBackgroundColorMenu = widgetFactory.createMenuItem();
        greenBackgroundColorMenu.setDescription("Green");
        greenBackgroundColorMenu.setActionCommand("BG-Green");
        // --------------------
        blueBackgroundColorMenu = widgetFactory.createMenuItem();
        blueBackgroundColorMenu.setDescription("Blue");
        blueBackgroundColorMenu.setActionCommand("BG-Blue");

        // 監聽指定選項
        redBackgroundColorMenu.addActionListener(actionListener);
        greenBackgroundColorMenu.addActionListener(actionListener);
        blueBackgroundColorMenu.addActionListener(actionListener);

        // 按鈕加入上級選單中
        colorMenu.addMenuItem(redBackgroundColorMenu);
        colorMenu.addMenuItem(greenBackgroundColorMenu);
        colorMenu.addMenuItem(blueBackgroundColorMenu);

        // --------------------------------------------------建立選單選項 [排版]--------------------------------------------------
        formatMenu = widgetFactory.createMenu();
        formatMenu.setDescription("Formatting");
        // 建立 [排版] 選單子選項
        // --------------------
        fullFormatMenu = widgetFactory.createMenuItem();
        fullFormatMenu.setDescription("Full");
        fullFormatMenu.setActionCommand("full");
        // --------------------
        plaintextFormatMenu = widgetFactory.createMenuItem();
        plaintextFormatMenu.setDescription("Plain Text");
        plaintextFormatMenu.setActionCommand("plaintext");

        // 監聽指定選項
        fullFormatMenu.addActionListener(actionListener);
        plaintextFormatMenu.addActionListener(actionListener);

        // 按鈕加入上級選單中
        formatMenu.addMenuItem(fullFormatMenu);
        formatMenu.addMenuItem(plaintextFormatMenu);

        // --------------------------------------------------建立選單選項 [幫助]--------------------------------------------------
        helpMenu = widgetFactory.createMenu();
        helpMenu.setDescription("Help");
        // 建立 [幫助] 選單子選項
        // --------------------
        aboutHelpMenu = widgetFactory.createMenuItem();
        aboutHelpMenu.setDescription("About");
        aboutHelpMenu.setActionCommand("about");

        // 監聽指定選項
        aboutHelpMenu.addActionListener(actionListener);

        // 按鈕加入上級選單中
        helpMenu.addMenuItem(aboutHelpMenu);

        // --------------------------------------------------建立選單選項[插入]----------------------------------------------------
        InserMenu = widgetFactory.createMenu();
        InserMenu.setDescription("Insert");
        // 建立 [設定] 選單子選項
        //-----------------------------------------
        Icon100 = widgetFactory.createMenuItem();
        Icon100.setDescription("Icon100");
        Icon100.setActionCommand("Icon100");
        //-----------------------------------------
        Icon200 = widgetFactory.createMenuItem();
        Icon200.setDescription("Icon200");
        Icon200.setActionCommand("Icon200");
        //-----------------------------------------
        Icon300 = widgetFactory.createMenuItem();
        Icon300.setDescription("Icon300");
        Icon300.setActionCommand("Icon300");
        //-----------------------------------------
        Icon400 = widgetFactory.createMenuItem();
        Icon400.setDescription("Icon400");
        Icon400.setActionCommand("Icon400");
        //-----------------------------------------
        Icon500 = widgetFactory.createMenuItem();
        Icon500.setDescription("Icon500");
        Icon500.setActionCommand("Icon500");
        // 監聽指定選項
        Icon100.addActionListener(actionListener);
        Icon200.addActionListener(actionListener);
        Icon300.addActionListener(actionListener);
        Icon400.addActionListener(actionListener);
        Icon500.addActionListener(actionListener);

        // 按鈕加入上級選單中
        InserMenu.addMenuItem(Icon100);
        InserMenu.addMenuItem(Icon200);
        InserMenu.addMenuItem(Icon300);
        InserMenu.addMenuItem(Icon400);
        InserMenu.addMenuItem(Icon500);

        // --------------------------------------------------按鈕加入上級選單中--------------------------------------------------
        bar.addMenu(fileMenu);
        bar.addMenu(editMenu);;
        bar.addMenu(formatMenu);
        bar.addMenu(colorMenu);
        bar.addMenu(fontStyleMenu);
        bar.addMenu(helpMenu);
        bar.addMenu(InserMenu);
        return bar;
    }

    public double buildSytaxTree(String strExp){
        calculater.build(strExp);
        if(calculater.compute()==-2.0){
            formulaError();
        }
        return calculater.compute();
    }

    public void formulaError(){
        Object[] options = { "是" };
        jOptionPane.showOptionDialog(null, "算式錯誤", "警告",  JOptionPane.DEFAULT_OPTION,
                JOptionPane.WARNING_MESSAGE,  null, options, options[0]);
//        System.out.println("error");
    }
}
