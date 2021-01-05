package com.paichi.modules.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLDecoder;

@Controller
public class UploadController {

    @RequestMapping("upload/index")
    public String uploadIndex() {
        return "upload";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(@RequestParam MultipartFile file) {
        // try {
        //     if(file.isEmpty()){
        //     }else{
        //         String conf_filename = this.getClass().getClassLoader().getResource("fast_client.conf").getPath().replaceAll("%20"," ");
        //         //conf_filename 路径/E:/workspace/paichi/代码/paichi/target/classes/fast_client.conf
        //         conf_filename = URLDecoder.decode(conf_filename.substring(1), "utf-8");
        //         String tempFileName = file.getOriginalFilename();
        //         //fastDFS方式
        //         ClientGlobal.init(conf_filename);
        //
        //         byte[] fileBuff = file.getBytes();
        //         String fileId = "";
        //         String fileExtName = tempFileName.substring(tempFileName.lastIndexOf(".") + 1);
        //
        //         //建立连接
        //         TrackerClient tracker = new TrackerClient();
        //         TrackerServer trackerServer = tracker.getTrackerServer();
        //         StorageServer storageServer = tracker.getStoreStorage(trackerServer);
        //         StorageClient1 client = new StorageClient1(trackerServer, storageServer);
        //
        //         //设置元信息
        //         /*
        //         NameValuePair[] metaList = new NameValuePair[3];
        //         metaList[0] = new NameValuePair("fileName", tempFileName);
        //         metaList[1] = new NameValuePair("fileExtName", fileExtName);
        //         metaList[2] = new NameValuePair("fileLength", String.valueOf(file.getSize()));
        //         */
        //
        //         //上传文件，获得fileId
        //             fileId = client.upload_file1(fileBuff, fileExtName, null);
		// 		//TODO 这里可以追加一些业务代码，例如上传成功后保存到upload_file表，统一进行上传文件管理之类
        //         System.out.println(fileId);
        //     }
        //
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
        return "upload";
    }
}

