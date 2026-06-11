import { useState } from "react";

import Container from "@mui/material/Container";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";

import UploadDocument from "./components/UploadDocument";
import AskQuestion from "./components/AskQuestion";
import Grid from "@mui/material/Grid";

function App() {
    const [documentId, setDocumentId] = useState("");

    return (
        <Container
            maxWidth="xl"
        >            <Box
            sx={{
                textAlign: "center",
                mb: 2,
            }}
        >
                <Typography
                    variant="h3"
                    gutterBottom
                >
                    AI Document Assistant
                </Typography>

                <Typography variant="h6" color="gray">
                    Upload PDFs and chat with your documents using AI
                </Typography>
            </Box>

            <Grid
                container
                spacing={3}
            >
                <Grid
                    size={{
                        xs: 12,
                        md: 6
                    }}
                >
                    <UploadDocument
                        onUploadSuccess={setDocumentId}
                    />
                </Grid>

                <Grid
                    size={{
                        xs: 12,
                        md: 6
                    }}
                >
                    <AskQuestion
                        documentId={documentId}
                    />
                </Grid>
            </Grid>
            <Box
                sx={{
                    textAlign: "center",
                    mt: 5,
                    mb: 3,
                }}
            >
                <Typography variant="body2" color="gray">
                    Powered by Spring Boot, PostgreSQL, pgvector and Gemini AI
                </Typography>
            </Box>
        </Container>
    );
}

export default App;
