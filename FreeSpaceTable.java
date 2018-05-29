package cn.edu.whu;

import java.util.ArrayList;

public class FreeSpaceTable {
    ArrayList<Area> fsTable;
    ArrayList<File> fileList;
    public static Area DeletedItem = new Area(-1, -1);

    public FreeSpaceTable() {
        fsTable = new ArrayList<>();
        fsTable.add(new Area(0, 24000));
        fileList = new ArrayList<>();
    }

    public boolean createFile(File file) {
        //allocspace的一个包装函数
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
        //寻找可以分配的表项
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
        return null;    //分配失败
    }
    public boolean freeSpace(String name) {
        File file = null;
        boolean flag = false;
        //在列表里查找对应文件
        for (int i = 0; i < fileList.size(); i++) {
            if (fileList.get(i).name.equals(name)) {
                file = fileList.get(i);
                flag = true;
            }
        }
        if(flag == false){
            return false;
        }
        //搜索列表，将需要合并的表项指定为 DeletedItem
        for (int i = 0; i < fsTable.size(); i++) {
            //分两种情况
            if (file.fileArea.StartIndex == fsTable.get(i).StartIndex + fsTable.get(i).blocks) {
                file.fileArea.StartIndex = fsTable.get(i).StartIndex;
                file.fileArea.blocks += fsTable.get(i).blocks;
                fsTable.set(i, FreeSpaceTable.DeletedItem);

            } else if (fsTable.get(i).StartIndex == file.fileArea.StartIndex + file.fileArea.blocks) {

                file.fileArea.blocks += fsTable.get(i).blocks;
                fsTable.set(i, FreeSpaceTable.DeletedItem);
            }
        }
        //删除两次，可能目标表项前后各有一个需要合并的表项
        fsTable.remove(FreeSpaceTable.DeletedItem);
        fsTable.remove(FreeSpaceTable.DeletedItem);
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
        //打印物理块号、磁道号和扇区号
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