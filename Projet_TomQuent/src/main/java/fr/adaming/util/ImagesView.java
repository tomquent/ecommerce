package fr.adaming.util;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class ImagesView {

	private List<String> imagesBanque;

	@PostConstruct
	public void init() {
		imagesBanque = new ArrayList<String>();
		for (int i = 1; i <= 3; i++) {
			imagesBanque.add("image" + i + ".jpg");
		}
	}

	public List<String> getImagesBanque() {
		return imagesBanque;
	}
}
