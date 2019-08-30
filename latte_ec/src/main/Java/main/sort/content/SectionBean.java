package main.sort.content;

import com.chad.library.adapter.base.entity.SectionEntity;

//SectionEntity分header，T content，这里content 为SectionContentItemEntity
public class SectionBean extends SectionEntity<SectionContentItemEntity> {
//    扩展的两个字段
    private boolean mIsMore=false;
    private int mId=-1;

    public boolean isMore() {
        return mIsMore;
    }

    public void setIsMore(boolean mIsMore) {
        this.mIsMore = mIsMore;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public SectionBean(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public SectionBean(SectionContentItemEntity sectionContentItemEntity) {
        super(sectionContentItemEntity);
    }
}
