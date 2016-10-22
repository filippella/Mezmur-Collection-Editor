/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mezmurcollectioneditor.ui;

import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import mezmurcollectioneditor.data.CategoryInfo;
import mezmurcollectioneditor.data.MezmurInfo;
import mezmurcollectioneditor.data.SelectedFonType;
import mezmurcollectioneditor.helper.FontHelper;
import mezmurcollectioneditor.keyboard.AmharicKeyboardAdapter;
import mezmurcollectioneditor.presentation.CollectionEditorView;
import mezmurcollectioneditor.presenter.CollectionEditorPresenter;
import mezmurcollectioneditor.renderer.MezmurRenderer;

/**
 *
 * @author Filippo
 */
public class CollectionEditorScreen extends javax.swing.JFrame implements CollectionEditorView {

    private final CollectionEditorPresenter presenter;
    private final DefaultListModel<MezmurInfo> mezmurListModel;
    private final DefaultComboBoxModel<CategoryInfo> categoryListModel, mezmurCategoryListModel;
    private final JList<MezmurInfo> mezmurList;
    private final AmharicKeyboardAdapter mezmurBodyAmharicKeyboardListener;
    private final AmharicKeyboardAdapter mezmurSearchFieldAmharicKeyboardListener;
    private final AmharicKeyboardAdapter mezmurTitleAmharicKeyboardListener;
    private final AmharicKeyboardAdapter mezmurExtraInfoAmharicKeyboardListener;

    /**
     * Creates new form CollectionEditorScreen
     */
    public CollectionEditorScreen() {
        initComponents();
        FontHelper fontHelper = new FontHelper();
        Font customFont = fontHelper.getCustomFont();
        this.mezmurListModel = new DefaultListModel<>();
        this.categoryListModel = new DefaultComboBoxModel<>();
        this.mezmurCategoryListModel = new DefaultComboBoxModel<>();
        this.mezmurList = new javax.swing.JList<>();
        this.jScrollPane1.setViewportView(this.mezmurList);
        this.mezmurList.setModel(this.mezmurListModel);
        CategoryInfo info = new CategoryInfo("*** ALL ** ");
        this.categoryListModel.addElement(info);
        this.mezmurCatecogySelectorList.setModel(this.categoryListModel);
        this.mezmurList.setCellRenderer(new MezmurRenderer(fontHelper));
        mezmurTitleTxt.setFont(fontHelper.getCustomFont());
        mezmurList.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    MezmurInfo mezmurInfo = mezmurList.getSelectedValue();
                    bindMezmurInfo(mezmurInfo);
                }
            }
        });

        this.presenter = new CollectionEditorPresenter(this);
        this.presenter.loadMezmur("mezmurcollectioneditor/assets/mezmur_new.json");
        this.presenter.loadCategories("mezmurcollectioneditor/assets/mezmur_categories.json");

        this.languageToggle.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ev) {
                if (ev.getStateChange() == ItemEvent.SELECTED) {
                    languageToggle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mezmurcollectioneditor/images/ic_amharic.png")));
                    setSelectedFont(SelectedFonType.AMHARIC);
                    //languageToggle.setText("Amharic");
                    System.out.println("button is selected");
                } else if (ev.getStateChange() == ItemEvent.DESELECTED) {
                    languageToggle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mezmurcollectioneditor/images/ic_english.png")));
                    setSelectedFont(SelectedFonType.ENGLISH);
                    //languageToggle.setText("English");
                    System.out.println("button is not selected");
                }
            }
        });

        this.mezmurCatecogySelectorList.setFont(customFont);
        this.mezmurSearchField.setFont(customFont);
        this.mezmurCategoryList.setFont(customFont);
        this.mezmurBodyTxt.setFont(customFont);
        this.mezmurExtraInfoTxt.setFont(customFont);

        this.mezmurBodyAmharicKeyboardListener = new AmharicKeyboardAdapter(this.mezmurBodyTxt);
        this.mezmurSearchFieldAmharicKeyboardListener = new AmharicKeyboardAdapter(this.mezmurSearchField);
        this.mezmurTitleAmharicKeyboardListener = new AmharicKeyboardAdapter(this.mezmurTitleTxt);
        this.mezmurExtraInfoAmharicKeyboardListener = new AmharicKeyboardAdapter(this.mezmurExtraInfoTxt);
    }

    /**
     * This method is used to bind selected mezmur info value into the editor
     *
     * @param mezmurInfo
     */
    private void bindMezmurInfo(MezmurInfo mezmurInfo) {
        mezmurIdTxt.setText(mezmurInfo.getMezmurId());
        mezmurTitleTxt.setText(mezmurInfo.getMezmurTitle());
        mezmurBodyTxt.setText(mezmurInfo.getMezmurBody());
        //mezmurCategoryList.setText(mezmurInfo.getMezmurTitle());
        mezmurExtraInfoTxt.setText(mezmurInfo.getMezmurExtraInfo());
        // mezmurStatusOption.setText(mezmurInfo.getMezmurTitle());
        mezmurOwnerTxt.setText(mezmurInfo.getMezmurOwner());
        mezmurCreatedDateTxt.setText(mezmurInfo.getMezmurCreatedDate());
        mezmurModifiedDateTxt.setText(mezmurInfo.getMezmurModifiedDate());
        mezmurAudioUrlTxt.setText(mezmurInfo.getMezmurAudioURL());
        mezmurVideoUrlTxt.setText(mezmurInfo.getMezmurVideoURL());
        this.mezmurCategoryListModel.removeAllElements();
        for (int i = 0; i < categoryListModel.getSize(); i++) {
            this.mezmurCategoryListModel.addElement(categoryListModel.getElementAt(i));
        }
        this.mezmurCategoryList.setModel(this.mezmurCategoryListModel);
        int itemCount = mezmurCategoryList.getItemCount();
        for (int i = 0; i < itemCount; i++) {
            CategoryInfo categoryInfo = (CategoryInfo) mezmurCategoryList.getItemAt(i);
            String cid = categoryInfo.getCid();
            if (cid != null && cid.equals(mezmurInfo.getMezmurCategoryId())) {
                this.mezmurCategoryList.setSelectedIndex(i);
                break;
            }
        }
    }

    /**
     * This method is used to select keyboard language
     *
     * @param selectedFonType
     */
    private void setSelectedFont(SelectedFonType selectedFonType) {
        switch (selectedFonType) {
            case AMHARIC:
                this.mezmurBodyTxt.addKeyListener(this.mezmurBodyAmharicKeyboardListener);
                this.mezmurSearchField.addKeyListener(this.mezmurSearchFieldAmharicKeyboardListener);
                this.mezmurTitleTxt.addKeyListener(this.mezmurTitleAmharicKeyboardListener);
                this.mezmurExtraInfoTxt.addKeyListener(this.mezmurExtraInfoAmharicKeyboardListener);
                break;
            case ENGLISH:
                this.mezmurBodyTxt.removeKeyListener(this.mezmurBodyAmharicKeyboardListener);
                this.mezmurSearchField.removeKeyListener(this.mezmurSearchFieldAmharicKeyboardListener);
                this.mezmurTitleTxt.removeKeyListener(this.mezmurTitleAmharicKeyboardListener);
                this.mezmurExtraInfoTxt.removeKeyListener(this.mezmurExtraInfoAmharicKeyboardListener);
                break;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        languageToggle = new javax.swing.JToggleButton();
        mezmurCatecogySelectorList = new javax.swing.JComboBox();
        mezmurSearchField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        javax.swing.JList mezmurList = new javax.swing.JList<MezmurInfo>();
        jScrollPane2 = new javax.swing.JScrollPane();
        mezmurBodyTxt = new javax.swing.JTextPane();
        jPanel6 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        mezmurIdTxt = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        mezmurCreatedDateTxt = new javax.swing.JTextField();
        mezmurModifiedDateTxt = new javax.swing.JTextField();
        mezmurTitleTxt = new javax.swing.JTextField();
        mezmurCategoryList = new javax.swing.JComboBox();
        jPanel7 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        mezmurAudioUrlTxt = new javax.swing.JTextField();
        mezmurVideoUrlTxt = new javax.swing.JTextField();
        mezmurOwnerTxt = new javax.swing.JTextField();
        mezmurExtraInfoTxt = new javax.swing.JTextField();
        mezmurStatusOption = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jToolBar1.setRollover(true);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mezmurcollectioneditor/images/ic_add.png"))); // NOI18N
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton1);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mezmurcollectioneditor/images/ic_remove.png"))); // NOI18N
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton2);

        languageToggle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mezmurcollectioneditor/images/ic_english.png"))); // NOI18N
        languageToggle.setText("Change Keyboard");
        languageToggle.setToolTipText("");
        languageToggle.setFocusable(false);
        languageToggle.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jToolBar1.add(languageToggle);

        mezmurCatecogySelectorList.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jScrollPane1.setViewportView(mezmurList);

        jScrollPane2.setViewportView(mezmurBodyTxt);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Basic Properties"));

        jLabel11.setText("Mezmur category:");

        jLabel12.setText("Mezmur modified date:");

        jLabel5.setText("Mezmur created date:");

        jLabel13.setText("Mezmur Title:");

        jLabel14.setText("Mezmur Id:");

        mezmurCategoryList.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addGap(55, 55, 55)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel13)
                                .addComponent(jLabel14)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                            .addGap(13, 13, 13)
                            .addComponent(jLabel5)))
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(mezmurCreatedDateTxt)
                    .addComponent(mezmurModifiedDateTxt)
                    .addComponent(mezmurTitleTxt)
                    .addComponent(mezmurIdTxt)
                    .addComponent(mezmurCategoryList, 0, 200, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel14))
                    .addComponent(mezmurIdTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mezmurTitleTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mezmurCreatedDateTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mezmurModifiedDateTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mezmurCategoryList, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addComponent(jLabel11))
                .addContainerGap())
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Additional Properties"));

        jLabel24.setText("Mezmur extra info:");

        jLabel25.setText("Mezmur video URL:");

        jLabel15.setText("Mezmur audio URL:");

        jLabel26.setText("Mezmur owner:");

        jLabel27.setText("Mezmur status:");

        mezmurStatusOption.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Active", "Inactive" }));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel27)
                            .addComponent(jLabel26)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jLabel25)
                            .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(mezmurOwnerTxt)
                    .addComponent(mezmurAudioUrlTxt)
                    .addComponent(mezmurVideoUrlTxt)
                    .addComponent(mezmurExtraInfoTxt)
                    .addComponent(mezmurStatusOption, 0, 200, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mezmurStatusOption, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mezmurOwnerTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mezmurAudioUrlTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mezmurVideoUrlTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mezmurExtraInfoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addContainerGap())
        );

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mezmurcollectioneditor/images/ic_search.png"))); // NOI18N

        jMenu1.setText("File");

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mezmurcollectioneditor/images/ic_add_small.png"))); // NOI18N
        jMenuItem1.setText("New");
        jMenu1.add(jMenuItem1);

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mezmurcollectioneditor/images/ic_open_small.png"))); // NOI18N
        jMenuItem2.setText("Open");
        jMenu1.add(jMenuItem2);

        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mezmurcollectioneditor/images/ic_save_small.gif"))); // NOI18N
        jMenuItem3.setText("Save");
        jMenu1.add(jMenuItem3);

        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mezmurcollectioneditor/images/ic_save_as_small.png"))); // NOI18N
        jMenuItem4.setText("Save As...");
        jMenu1.add(jMenuItem4);

        jMenuItem5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mezmurcollectioneditor/images/ic_exit_small.png"))); // NOI18N
        jMenuItem5.setText("Exit");
        jMenu1.add(jMenuItem5);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");

        jMenuItem6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mezmurcollectioneditor/images/ic_check_duplicates_small.png"))); // NOI18N
        jMenuItem6.setText("Check for Duplicates");
        jMenu2.add(jMenuItem6);

        jMenuItem7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mezmurcollectioneditor/images/ic_sort_small.png"))); // NOI18N
        jMenuItem7.setText("Sort Alphabetically");
        jMenu2.add(jMenuItem7);

        jMenuItem8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mezmurcollectioneditor/images/ic_category_sort_small.png"))); // NOI18N
        jMenuItem8.setText("Sort By Category");
        jMenu2.add(jMenuItem8);

        jMenuItem9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mezmurcollectioneditor/images/ic_import_small.png"))); // NOI18N
        jMenuItem9.setText("Import File");
        jMenu2.add(jMenuItem9);

        jMenuItem10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mezmurcollectioneditor/images/ic_export_small.png"))); // NOI18N
        jMenuItem10.setText("Export As");
        jMenu2.add(jMenuItem10);

        jMenuBar1.add(jMenu2);

        jMenu4.setText("Preferences");

        jMenuItem13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mezmurcollectioneditor/images/ic_settings_small.png"))); // NOI18N
        jMenuItem13.setText("Settings");
        jMenu4.add(jMenuItem13);

        jMenuBar1.add(jMenu4);

        jMenu3.setText("Help");

        jMenuItem11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mezmurcollectioneditor/images/ic_about_small.png"))); // NOI18N
        jMenuItem11.setText("About");
        jMenu3.add(jMenuItem11);

        jMenuItem12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mezmurcollectioneditor/images/ic_help_small.png"))); // NOI18N
        jMenuItem12.setText("Help");
        jMenu3.add(jMenuItem12);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(mezmurSearchField, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(mezmurCatecogySelectorList, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2))
                .addContainerGap())
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(mezmurSearchField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mezmurCatecogySelectorList, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToggleButton languageToggle;
    private javax.swing.JTextField mezmurAudioUrlTxt;
    private javax.swing.JTextPane mezmurBodyTxt;
    private javax.swing.JComboBox mezmurCatecogySelectorList;
    private javax.swing.JComboBox mezmurCategoryList;
    private javax.swing.JTextField mezmurCreatedDateTxt;
    private javax.swing.JTextField mezmurExtraInfoTxt;
    private javax.swing.JTextField mezmurIdTxt;
    private javax.swing.JTextField mezmurModifiedDateTxt;
    private javax.swing.JTextField mezmurOwnerTxt;
    private javax.swing.JTextField mezmurSearchField;
    private javax.swing.JComboBox mezmurStatusOption;
    private javax.swing.JTextField mezmurTitleTxt;
    private javax.swing.JTextField mezmurVideoUrlTxt;
    // End of variables declaration//GEN-END:variables

    @Override
    public void onAppendMezmur(MezmurInfo data) {
        this.mezmurListModel.addElement(data);
        //System.out.println("Model size = " + mezmurListModel.size());
    }

    @Override
    public void onError(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    @Override
    public void onAppendCategory(CategoryInfo data) {
        this.categoryListModel.addElement(data);
    }
}
