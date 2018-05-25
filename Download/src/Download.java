import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

public class Download {
    static public void get(String url,String id){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println(response.body().string());
                if (response.body().contentType().toString().startsWith("image")){
                    System.out.println("img");
                }
            }
        });
    }
    static public StringBuilder readfile(String filename){
        StringBuilder s=new StringBuilder();
        File file=new File(filename);
        try {
            FileInputStream fileInputStream=new FileInputStream(file);
            byte[] bytes=new byte[4096];
            int len;
            while ((len=fileInputStream.read(bytes))!=-1){
                System.out.print(new String(bytes,0,len));
                s.append(new String(bytes,0,len));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }
    public static void main(String args[]){
        StringBuilder id_json=readfile("ability_ids.json");
        Map<String,String> ids=new Gson().fromJson(id_json.toString(),new TypeToken<Map<String, String>>() {}.getType());
        StringBuilder abilities_json=readfile("abilities.json");
        Map<String,Ability> abilities=new Gson().fromJson(abilities_json.toString(),new TypeToken<Map<String, Ability>>() {}.getType());
        for(Map.Entry<String, Ability> entry : abilities.entrySet()){
            System.out.println(entry.getKey()+" "+entry.getValue().getDname());
            if (entry.getValue().getImg()!=null){
                String id="";
                for (Map.Entry<String,String> entry1:ids.entrySet()){
                    if (entry1.getValue().equals(entry.getKey())) {
                        id=entry1.getKey();
                        break;
                    }
                }

            }
        }
    }
}
