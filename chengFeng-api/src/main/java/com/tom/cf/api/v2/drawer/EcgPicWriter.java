package com.tom.cf.api.v2.drawer;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class EcgPicWriter implements Writer{

    private File outFile;
    private PaperDrawer drawer;

    public EcgPicWriter(File outFile, PaperDrawer drawer) {
        this.outFile = outFile;
        this.drawer = drawer;
    }

    @Override
    public File write() {
        try {
            String fileType = outFile.getName().substring(outFile.getName().lastIndexOf(".")+1);
            ImageIO.write(drawer.draw(), fileType, outFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return outFile;
    }

    public File getOutFile() {
        return outFile;
    }

    public void setOutFile(File outFile) {
        this.outFile = outFile;
    }

    public PaperDrawer getDrawer() {
        return drawer;
    }

    public void setDrawer(PaperDrawer drawer) {
        this.drawer = drawer;
    }
}
