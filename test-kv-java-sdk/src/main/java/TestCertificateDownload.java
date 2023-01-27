import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.azure.identity.DefaultAzureCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import com.azure.security.keyvault.secrets.models.KeyVaultSecret;

public class TestCertificateDownload {
	static final Logger logger = LoggerFactory.getLogger(TestCertificateDownload.class);
	public static String KEY_VAULT_URL = "https://kv-java-test.vault.azure.net"; 
	public static String AZURE_CLIENT_ID = "1b09e5d5-5432-447b-9525-e218015b5727";
	public static String CERTIFICATE_KEY ="test-java-sdk";
	DefaultAzureCredential defaultCredential = null;
	
	public static void main(String[] args) throws Exception {
		TestCertificateDownload download = new TestCertificateDownload();
		logger.info("Starting up now");
		download.getCertificateSecret();
	}

	public void test() throws Exception {
		
		getCertificateSecret();
		
	}

	public void getCertificateSecret() throws IOException {
		
		defaultCredential = new DefaultAzureCredentialBuilder()
				.additionallyAllowedTenants("*")
				.build();
		logger.info("Built Default Credential!!!");
		
		// Azure SDK client builders accept the credential as a parameter
		SecretClient client = new SecretClientBuilder().vaultUrl("https://kv-java-test.vault.azure.net")
				.credential(defaultCredential).buildClient();
		logger.info("Built Secret Client!!!");
		KeyVaultSecret secret = client.getSecret("test-java-sdk");
		byte [] certificate = Base64.getDecoder().decode(secret.getValue());
		
		Files.write(Paths.get("/Users/gvnavp/Documents/Prasad/development/az-kv-certificate.pfx"), certificate);
		System.out.printf("Retrieved secret with name \"%s\" and value \"%s\"%n", secret.getName(), secret.getValue());
	}
	
	
}
