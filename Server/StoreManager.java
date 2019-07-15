import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
/*
 * StoreManager.
 *
 * Creates the store manager user interface.
 *
 * @version 1.01 February 2019
 */
public class StoreManager extends JFrame {
    String midiFile;
    String coverArtFile;
    String titleName;
    String BundleName;

    /*
     * The constructor that sets up the GUI
     */
    public StoreManager() {
        initComponents();
        setVisible(true);
        pack();
    }

    // Initializing GUI variables
    JLabel CoverArtLabel = new JLabel();
    JLabel MusicLabel = new JLabel();
    JLabel TitleLabel = new JLabel();
    JButton btCoverArtBrowse = new JButton();
    JButton btMusicBrowse = new JButton();
    JButton btSave = new JButton();
    JButton btTitleBrowse = new JButton();
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JTextField tfCoverArt = new JTextField();
    JTextField tfMusic = new JTextField();
    JTextField tfTitle = new JTextField();
    JFrame frame;

    /*
     * Function to create the layout of the Store Manager GUI
     */
    private void initComponents() {

        // Creating a new frame
        frame = new JFrame();
        frame.getContentPane().setLayout(null);
        setTitle("Store Manager"); // Adding heading title
        setBackground(new Color(229, 225, 208)); // Setting background colour for the full frame

        jPanel1.setBackground(new Color(229, 225, 208));
        jPanel1.setBorder(BorderFactory.createEtchedBorder()); // Creating border around the title, cover art, and music fields

        TitleLabel.setBackground(new Color(229, 225, 208));
        TitleLabel.setText("Title:"); // Creating the "Title" label

        tfTitle.setBounds(10, 10, 414, 21); // Setting the positioning of "Title" field
        frame.getContentPane().add(tfTitle); // Adding the "Title" field to GUI

        btTitleBrowse.setText("Browse");
        frame.getContentPane().add(btTitleBrowse); // Adding title "Browse" button to GUI

        // Adding action listeners for when the title "Browse" button is clicked
        btTitleBrowse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btTitleBrowseActionPerformed(evt);
            }
        });

        CoverArtLabel.setBackground(new Color(229, 225, 208));
        CoverArtLabel.setText("Covert art:");

        MusicLabel.setBackground(new Color(229, 225, 208));
        MusicLabel.setText("Music:");

        btCoverArtBrowse.setText("Browse");

        // Adding action listeners for when the cover art "Browse" button is clicked
        btCoverArtBrowse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btCoverArtBrowseActionPerformed(evt);
            }
        });

        btMusicBrowse.setText("Browse");

        // Adding action listeners for when the music "Browse" button is clicked
        btMusicBrowse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btMusicBrowseActionPerformed(evt);
            }
        });

        // Creating layout for first panel containing the "Title", "Cover art", and "Music" field group
        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout); // Assigning layout

        // Creating the horizontal alignment of the panel
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup() // Creating a group that positions and sizes its elements one after another
                // Determining the baselines of the group
                .addGap(18, 18, 18) // Adds a horizontal rigid gap to the group before all the component fields are added
                // Creates group for text field labels in which the fields are parallel to one another at equal distances
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    // Adds the text field labels with minimum size, preferred size, and maximum size parameters
                    .addComponent(TitleLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(CoverArtLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(MusicLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(ComponentPlacement.RELATED) // Adds an element representing the preferred gap between the nearest components
                // Creates group for text fields in which the fields are parallel to one another at equal distance
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                    // Adding the text field components
                    .addComponent(tfCoverArt)
                    .addComponent(tfTitle)
                    .addComponent(tfMusic, GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE))
                .addPreferredGap(ComponentPlacement.RELATED, 10, Short.MAX_VALUE) // Adds an element representing the preferred gap between the nearest components
                // Creates group for the buttons in which the buttons are parallel to one another at equal distance
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(btTitleBrowse)
                    .addComponent(btCoverArtBrowse)
                    .addComponent(btMusicBrowse, GroupLayout.Alignment.LEADING))
                .addContainerGap()) // Adds an element representing the preferred gap between the edge of the container and components that touch the border of the container
        );

        // Creating the vertical alignment of the panel
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE) // Adds an element representing the preferred gap between one edge of the container and the next or previous component with the specified size
                // Creates group for title label, field, and button parallel to one another at equal distances
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    // Adding the title label, field and button components
                    .addComponent(TitleLabel)
                    .addComponent(tfTitle, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btTitleBrowse, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(ComponentPlacement.RELATED) // Adds an element representing the preferred gap between the nearest components
                // Adding the cover art label, fields and button aligning with one another
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    // Adding the cover art label, field and button components
                    .addComponent(CoverArtLabel)
                    .addComponent(tfCoverArt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btCoverArtBrowse, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(ComponentPlacement.RELATED) // Adds an element representing the preferred gap between the nearest components
                // Adding the music label, fields and button aligning with one another
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    // Adding the music label, field and button components
                    .addComponent(MusicLabel)
                    .addComponent(tfMusic, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btMusicBrowse, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
                .addGap(91, 91, 91)) // Adds a vertical gap below the components with the specified size
        );


        jPanel2.setBackground(new Color(229, 225, 208));
        jPanel2.setBorder(BorderFactory.createEtchedBorder()); // Creates border around the second panel containing the "Save" button

        // Adding action listeners for when the cover art "Save" button is clicked
        btSave.setText("Save");
        btSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btSaveActionPerformed(evt);
            }
        });

        // Creating a second panel layout for the "Save" button group to allow for the seperate border
        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);

        // Creating the horizontal alignment of the second panel
        jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup() // Creating a group that positions and sizes its elements one after another
                .addGap(107, 107, 107) // Adds a horizontal gap before the "Save" button with the specified size
                .addComponent(btSave, GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                .addGap(120, 120, 120)) // Adds a horizontal gap after the "Save" button with the specified size
        );

        // Creating the vertical alignment of the second panel
        jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18) // Adds a vertical gap before the "Save" button with the specified size
                .addComponent(btSave) // Add the "Save" button component
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)) // Adds an element representing the preferred gap between the edge of the container and the button that touches the border of the container
        );

        // Creating a new layout to combine the previous to panel layouts
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        // Setting the horizontal sizes and placements of the overall group layout
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING) // to ensure the components are parallel to one another
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18) // Adds a horizontal gap before the first panel is added
                .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE) // Adding the first panel
                .addContainerGap()) // Adding the preferred horizontal gap after the first panel is added
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap() // Adding the preferred horizontal gap before the second panel is added
                .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE) // Adding the second panel
                .addContainerGap()) // Adds the preferred horizontal gap after the second panel is added
        );
        // Setting the vertical sizes and placements of the overall group layout
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING) // to ensure the components are parallel to one another
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }

    /*
     * Function to implement browsing features of the Title button
     */
    private void btTitleBrowseActionPerformed(ActionEvent evt) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY); // ensures that only files can be selected
        fileChooser.setAcceptAllFileFilterUsed(false); // only allows the specific file types to be displayed
        FileNameExtensionFilter filter = new FileNameExtensionFilter("MIDI Files", "mid"); // Sets the file type filter to view only MIDI files
        fileChooser.setFileFilter(filter); // implements the file type filter

        int filename = fileChooser.showOpenDialog(null);

        if (filename == JFileChooser.APPROVE_OPTION) {
            tfTitle.setText(fileChooser.getSelectedFile().getName()); // Converts the selected file type to a string to be saved and viewed in the text field
        }
        titleName = fileChooser.getSelectedFile().toString();
        BundleName = (" " + fileChooser.getSelectedFile().getName()).replaceFirst("[.][^.]+$", ""); // Removes file extension
        System.out.println("" + titleName); // Prints the selected filename
    }

    /*
     * Function to implement browsing features of the Cover art button
     */
    private void btCoverArtBrowseActionPerformed(ActionEvent evt) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY); // ensures that only files can be selected
        fileChooser.setAcceptAllFileFilterUsed(false); // only allows the specific file types to be displayed
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image File", "png"); // Sets the file type filter to view only PNG files
        fileChooser.setFileFilter(filter);

        int filename = fileChooser.showOpenDialog(null);
        if (filename == JFileChooser.APPROVE_OPTION) {
            tfCoverArt.setText(fileChooser.getSelectedFile().getName()); // Converts the selected file type to a string to be saved and viewed in the text field
        }
        coverArtFile = fileChooser.getSelectedFile().toString();
    //    coverArtFile = (" " + fileChooser.getSelectedFile().getName()).replaceFirst("[.][^.]+$", ""); // Removes file extension
        System.out.println("" + coverArtFile); // Prints the selected filename
    }

    /*
     * Function to implement browsing features of the Music button
     */
    private void btMusicBrowseActionPerformed(ActionEvent evt) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY); // ensures that only files can be selected
        fileChooser.setAcceptAllFileFilterUsed(false); // only allows the specific file types to be displayed
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Midi File", "mid"); // Sets the file type filter to view only midi files
        fileChooser.setFileFilter(filter);

        int filename = fileChooser.showOpenDialog(null);

        if (filename == JFileChooser.APPROVE_OPTION) {
            tfMusic.setText(fileChooser.getSelectedFile().getName()); // Converts the selected file type to a string to be saved and viewed in the text field
        }
        midiFile = fileChooser.getSelectedFile().toString();
        //midiFile = (" " + fileChooser.getSelectedFile().getName()).replaceFirst("[.][^.]+$", ""); // Removes file extension
        System.out.println("" + midiFile); // Prints the selected filename
        GuitarGenerator g = new GuitarGenerator(midiFile);
    }

    /*
     * Function to implement browsing features of the Save button
     */
    private void btSaveActionPerformed(ActionEvent evt) {
        // TODO Save Bundle in zip format to server
        // Currently hardcoded until client-server set up
        try {
            FileOutputStream fos = new FileOutputStream(BundleName + ".zip");
            ZipOutputStream zos = new ZipOutputStream(fos);

          //  GuitarGenerator g = new GuitarGenerator(midiFile);

            String file1Name = titleName;
            String file2Name = coverArtFile;
            String file3Name = "notes.txt";

            addToZipFile(file1Name, zos);
            addToZipFile(file2Name, zos);
            addToZipFile(file3Name, zos);

            zos.close();
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    public static void addToZipFile(String fileName, ZipOutputStream zos) throws FileNotFoundException, IOException {
        System.out.println("Writing '" + fileName + "' to zip file");

        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zos.putNextEntry(zipEntry);

        byte[] bytes = new byte[1024]; // Converts the filename value into a byte array, 1024 buffer size
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zos.write(bytes, 0, length); // Takes the byte array and writes it to the ZipEntry within the zip file.
        }

        zos.closeEntry();
        fis.close();
    }

    public static void main(String[] args) {
        StoreManager sm = new StoreManager();
    }
}
