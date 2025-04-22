package org.example.tpo_07.service;

import jakarta.annotation.PostConstruct;
import org.example.tpo_07.serializable.CodeSnippet;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

@Service
public class CodeSnippetService {

    private final String FILE_PATH = "snippets.ser";
    private final long EXPIRATION_INTERVAL_MS = 5000; //checking every 5 seconds for expiration of snippets...

    @PostConstruct
    public void startCleanerThread(){

        new Thread(() -> {
            while(true){
                try{
                    Map<String, CodeSnippet> snippets = loadAll();
                    boolean updated = false;

                    Iterator<Map.Entry<String, CodeSnippet>> iterator = snippets.entrySet().iterator();
                    while(iterator.hasNext()){

                        Map.Entry<String, CodeSnippet> entry = iterator.next();

                        if(entry.getValue().getExpirationDate().isBefore(LocalDateTime.now())){
                            iterator.remove();
                            updated = true;
                        }
                    }

                    if(updated){
                        saveAll(snippets);
                    }

                    Thread.sleep(EXPIRATION_INTERVAL_MS);


                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "SnippetCleanerThread").start();

    }


    public void saveCodeSnippet(String id, String originalCode, String formattedCode, long expDateSeconds) {
        Map<String, CodeSnippet> snippets = loadAll();

        if(snippets.containsKey(id)){
            throw new IllegalArgumentException("A snippet with this ID already exists.");
        }

        CodeSnippet snippet = new CodeSnippet(id,
                originalCode,
                formattedCode,
                LocalDateTime.now().plusSeconds(expDateSeconds));


        snippets.put(id, snippet);
        saveAll(snippets);
    }

    public Optional<CodeSnippet> loadSnippet(String id) {
        Map<String, CodeSnippet> snippets = loadAll();
        CodeSnippet snippet = snippets.get(id);

        if (snippet == null) return Optional.empty();

        if (LocalDateTime.now().isAfter(snippet.getExpirationDate())) {
            snippets.remove(id);
            saveAll(snippets);
            return Optional.empty();
        }

        return Optional.of(snippet);
    }



    public Map<String, CodeSnippet> loadAll() {
        File file = new File(FILE_PATH);

        if(!file.exists()){
            return new HashMap<>();
        }

        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))){

            return (Map<String, CodeSnippet>) ois.readObject();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveAll(Map<String, CodeSnippet> snippets) {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(snippets);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
