package com.ygsoft.rss;

import java.io.File;
import java.util.List;
import com.ygsoft.rss.data.NewInfo;;

public interface IDocBuilder {
	public void createDoc(List<NewInfo> newInfos, File targetFile);
}
