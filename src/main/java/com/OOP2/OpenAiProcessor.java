package com.OOP2;

import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.tika.Tika;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;


public class OpenAiProcessor {
    private String filePath;
    private String apiKey;

    public OpenAiProcessor(String filePath, String apiKey) {
        this.filePath = filePath;
        this.apiKey = apiKey;
    }

    public String getFileContent() throws IOException {
        String fileContent;
        File file = new File(filePath);
        Tika tika = new Tika();
        String mimeType = tika.detect(file);

        if (mimeType.equals("application/pdf")) {
            PDDocument document = PDDocument.load(file);
            fileContent = new PDFTextStripper().getText(document);
            document.close();
        } else {
            fileContent = new String(Files.readAllBytes(file.toPath()));
        }

        return fileContent;
    }

    public String callOpenAiApi() throws IOException, InterruptedException {
        String fileContent = getFileContent();
        String jsonPayload = "{\"prompt\": \"" + fileContent + "\", \"max_tokens\": 50}";

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/engines/davinci-codex/completions"))
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
