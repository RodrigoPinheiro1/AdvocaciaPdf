package com.van.advogaciapdf.service;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.van.advogaciapdf.dto.EmailDetailsDto;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;


    String outorgante = "SACI DE PATINETE";
    String cpf = "123.456.789-00";
    String rg = "MG-12.345.678";
    String endereco = "Rua dos Sacis, 1234, Bairro Saci, Cidade Saci";
    String outorgado = "VINYCIUS AMARAL ALVARENGA";
    String oab = "19.442";
    String data = "20 de setembro de 2024";
    String cidade = "Manaus";


    public byte[] generateProcuraPdf() {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);
            com.itextpdf.kernel.pdf.PdfDocument pdfDoc = new com.itextpdf.kernel.pdf.PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Título
            Paragraph title = new Paragraph("PROCURAÇÃO")
                    .setBold()
                    .setFontSize(18)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20);
            document.add(title);

            // Corpo da procuração com placeholders preenchidos dinamicamente
            String procuraContent = String.format(
                    """
                            OUTORGANTE: %s, cadastrado no CPF de nº %s, portador do RG nº %s, residente e domiciliado à %s.
                            \s
                            OUTORGADO: %s, advogado, inscrito na OAB sob nº %s.
                            \s
                            PODERES: A OUTORGANTE confere ao OUTORGADO amplos poderes ad e extra judicia, inerentes ao bom e fiel cumprimento deste mandato,
                            bem como para o foro em geral, conforme estabelecido no artigo 105 do Código de Processo Civil/2015, e os especiais para transigir,
                            firmar compromisso, substabelecer, renunciar, desistir, reconhecer a procedência do pedido, recepcionar intimações e notificações,
                            receber e dar quitação, praticar todos os atos perante repartições públicas no âmbito Federal, Estadual e Municipal, e órgãos da
                            administração pública direta e indireta, praticar quaisquer atos perante particulares ou empresas privadas, recorrer a quaisquer
                            instâncias e tribunais, em especial, de o representar em processos trabalhistas podendo atuar em conjunto ou separadamente,
                            dando tudo por bom, firme e valioso.
                            \s
                            %s, %s
                            \s
                            _____________________________________________________
                            OUTORGANTE
                           \s""".formatted(outorgante, cpf, rg, endereco, outorgado, oab, cidade, data));

            // Adiciona o texto da procuração
            Paragraph content = new Paragraph(procuraContent)
                    .setTextAlignment(TextAlignment.JUSTIFIED)
                    .setFontSize(12)
                    .setMarginBottom(10);
            document.add(content);

            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar PDF", e);
        }
    }


    public void mandarEmail(EmailDetailsDto emailDetailsDto) throws Exception {

        try {

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

            messageHelper.setTo(emailDetailsDto.getRecebedor());
            messageHelper.setSubject(emailDetailsDto.getAssunto());
            messageHelper.setText(emailDetailsDto.getTexto());


            byte[] pdfData = generateProcuraPdf();

            // Adicionar o PDF como anexo
            ByteArrayResource attachment = new ByteArrayResource(pdfData);
            messageHelper.addAttachment("documento.pdf", attachment);


            mailSender.send(mimeMessage);

        } catch (Exception exception) {
//            LOGGER.error("error while sending the mail");
            throw new Exception("error while sending the mail : "
                    + exception.getMessage());
        }
    }
}
