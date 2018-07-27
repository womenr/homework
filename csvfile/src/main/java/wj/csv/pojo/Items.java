package wj.csv.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

@SuppressWarnings("serial")
public class Items implements Serializable{ //为了在页面可以通过session来提取该对象的数据信息，必须使用序列化的对象

    private String item;

    private BigDecimal price;

    private String info;

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item == null ? null : item.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info == null ? null : info.trim();
    }

	@Override
	public String toString() {
		return "Items [item=" + item + ", price=" + price + ", info=" + info + "]";
	}
}