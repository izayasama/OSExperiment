package cn.edu.whu;

import java.util.ArrayList;

public class FreeSpaceTable {
    ArrayList<Area> fsTable;
    ArrayList<File> fileList;

    public FreeSpaceTable() {
        fsTable = new ArrayList<>();
        fsTable.add(new Area(0, 24000));
        fileList = new ArrayList<>();
    }

    public boolean createFile(File file) {
        Area area;
        area = allocSpace(file.fileArea.blocks);
        if (area == null) {
            return false;
        } else {
            file.fileArea = area;
            fileList.add(file);
            return true;
        }
    }

    public Area allocSpace(int size) {
        for (Area fsArea :
                fsTable) {
            if (fsArea.blocks > size) {
                fsTable.remove(fsArea);
                fsTable.add(new Area(fsArea.StartIndex + size, fsArea.blocks - size));
                return new Area(fsArea.StartIndex, size);
            } else if (fsArea.blocks == size) {
                fsTable.remove(fsArea);
                return new Area(fsArea.StartIndex, size);
            }
        }
        return null;
    }
    public boolean freeSpace(String name) {
        File file = null;
        boolean flag = false;
        for (int i = 0; i < fileList.size(); i++) {
            if (fileList.get(i).name.equals(name)) {
                file = fileList.get(i);
                flag = true;
            }
        }
        if(flag == false){
            return false;
        }
        for (Area fsArea :
                fsTable) {
            if (file.fileArea.StartIndex == fsArea.StartIndex + fsArea.blocks) {
                fsTable.remove(fsArea);
                file.fileArea.StartIndex = fsArea.StartIndex;
                file.fileArea.blocks += fsArea.blocks;
            } else if (fsArea.StartIndex == file.fileArea.StartIndex + file.fileArea.blocks) {
                fsTable.remove(fsArea);
                file.fileArea.blocks += fsArea.blocks;
            }
        }
        fsTable.add(file.fileArea);
        fileList.remove(file);
        return true;

    }
}

class Area {
    int StartIndex;
    int blocks;

    public Area(int startIndex, int blocks) {
        StartIndex = startIndex;
        this.blocks = blocks;
    }

    public void showDiskInfo (){
        System.out.println("Physical Record\tTrack\tCylinder");
        System.out.println(((StartIndex) % 6) + "\t\t\t" + (((StartIndex) / 6)%20) + "\t\t\t\t" + (((StartIndex) / 6) / 20));
    }

    @Override
    public String toString() {
        return StartIndex + "\t\t\t" + blocks;
    }
}
class File  {
    String name;
    Area fileArea;

    public File(int size, String name) {
        fileArea = new Area(0, size);
        this.name = name;
    }
}