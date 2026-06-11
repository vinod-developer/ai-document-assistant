import { useState } from "react";
import api from "../api/documentApi";

import {
    Box,
    Button,
    Card,
    CardContent,
    CircularProgress,
    TextField,
    Typography,
} from "@mui/material";

type Props = {
    documentId: string;
};

function AskQuestion({ documentId }: Props) {
    const [question, setQuestion] = useState("");

    const [answer, setAnswer] = useState("");

    const [loading, setLoading] = useState(false);

    const handleAsk = async () => {
        setLoading(true);

        try {
            const response = await api.post("/api/documents/question", {
                documentId,
                question,
            });

            setAnswer(response.data.answer);
        } catch (error) {
            console.error(error);

            setAnswer("Failed to get answer");
        } finally {
            setLoading(false);
        }
    };

    return (
        <Card
            sx={{
                borderRadius: 3
            }}
        >
            <CardContent>
                <Typography
                    variant="h5"
                    gutterBottom
                    sx={{
                        textAlign: "center",
                    }}
                >
                    🤖 Ask AI
                </Typography>
                <Box
                    sx={{
                        display: "flex",
                        flexDirection: "column",
                        gap: 2,
                    }}
                >
                    <TextField
                        label="Document ID"
                        value={documentId}
                        fullWidth
                        disabled
                    />

                    <TextField
                        label="Question"
                        value={question}
                        onChange={(event) => setQuestion(event.target.value)}
                        fullWidth
                        multiline
                        rows={4}
                    />

                    <Button
                        variant="contained"
                        size="large"
                        fullWidth
                        sx={{
                            height: 50,
                        }}
                        onClick={handleAsk}
                        disabled={loading || !documentId}
                    >
                        {loading ? (
                            <CircularProgress size={24} color="inherit" />
                        ) : (
                            "Ask AI"
                        )}
                    </Button>
                </Box>
            </CardContent>

            {answer && (
                <CardContent>

                    <Typography
                        variant="h6"
                        gutterBottom
                    >
                        🤖 AI Answer
                    </Typography>

                    <Box
                        sx={{
                            maxHeight: 250,
                            overflowY: "auto",
                            border: "1px solid #e0e0e0",
                            borderRadius: 2,
                            p: 2,
                            backgroundColor: "#fafafa"
                        }}
                    >
                        <Typography
                            variant="body1"
                            sx={{
                                whiteSpace: "pre-wrap",
                                lineHeight: 1.8,
                                textAlign: "left"
                            }}
                        >
                            {answer}
                        </Typography>
                    </Box>

                </CardContent>
            )}
        </Card>
    );
}

export default AskQuestion;
