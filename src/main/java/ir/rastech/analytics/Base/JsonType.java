package ir.rastech.analytics.Base;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;

/*import me.telemart.man.base.exception.InvalidArgumentException;
import me.telemart.man.base.image.DocumentProperties;
import me.telemart.man.domain.entity.appInstance.ProviderTemplateProperties;
import me.telemart.man.domain.entity.chart.Chart;
import me.telemart.man.domain.entity.offer.InvoiceContent;
import me.telemart.man.domain.entity.provider.ProviderItemProperties;
import me.telemart.man.domain.entity.provider.PublicProperties;
import me.telemart.man.domain.entity.provider.items.ProviderDocumentProperties;
import me.telemart.man.domain.entity.provider.items.ProviderSearchTagItem;
import me.telemart.man.domain.entity.provider.items.chart.ProviderChart;
import me.telemart.man.domain.entity.provider.items.folder.ProviderFolder;
import me.telemart.man.domain.entity.provider.items.survey.ProviderSurvey.ProviderSurveyProperties;
import me.telemart.man.domain.entity.provider.items.survey.ProviderSurveyReportProperties;
import me.telemart.man.domain.entity.survey.SurveyProperties;
import me.telemart.man.domain.entity.survey.answer.AnswerProperties;
import me.telemart.man.domain.entity.survey.question.*;
import me.telemart.man.domain.entity.survey.report.SurveyReportProperties;
import me.telemart.man.domain.entity.task.company.Company;
import me.telemart.man.domain.entity.task.company.CompanyMember;
import me.telemart.man.domain.entity.task.company.CompanyTemplateProperties;
import me.telemart.man.domain.entity.task.project.Project;
import me.telemart.man.domain.entity.task.project.ProjectMember;
import me.telemart.man.domain.entity.task.task.TaskProperties;
import me.telemart.man.domain.entity.task.task.TemplateProperties;
import me.telemart.man.domain.entity.utils.AddressProperties;
import me.telemart.man.ui.objects.survey.SurveyResponseView;*/

//import org.hibernate.engine.spi.SharedSessionContractImplementor;

public class JsonType implements UserType, ParameterizedType {

    public static final String LIST_TYPE = "LIST";
    public static final String SET_TYPE = "SET";
    //public static final String INVOICE_CONTENT = "INVOICE_CONTENT";
    public static final String MAP_TYPE = "MAP_TYPE";
    /*public static final String PROVIDER_PUBLIC_PROPERTIES = "PROVIDER_PUBLIC_PROPERTIES";
    public static final String DOCUMENT_PROPERTIES = "DOCUMENT_PROPERTIES";
    public static final String PROVIDER_ITEM_PROPERTIES = "PROVIDER_ITEM_PROPERTIES";
    public static final String SURVEY_RESPONSE_VIEW = "SURVEY_RESPONSE_VIEW";
    public static final String SURVEY_PROPERTIES = "SURVEY_PROPERTIES";
    public static final String SURVEY_QUESTION_PROPERTIES = "SURVEY_QUESTION_PROPERTIES";
    public static final String SURVEY_ANSWER_PROPERTIES = "SURVEY_ANSWER_PROPERTIES";
    public static final String PROVIDER_CHART = "PROVIDER_CHART";
    public static final String TEMPLATE_PROPERTIES = "TEMPLATE_PROPERTIES";
    public static final String PROVIDER_TEMPLATE_PROPERTIES = "PROVIDER_TEMPLATE_PROPERTIES";
    public static final String COMPANY_TEMPLATE_PROPERTIES = "COMPANY_TEMPLATE_PROPERTIES";
    public static final String TASK_PROPERTIES = "TASK_PROPERTIES";
    public static final String ADDRESS_PROPERTIES = "ADDRESS_PROPERTIES";
    public static final String PROJECT_PROPERTIES = "PROJECT_PROPERTIES";
    public static final String PROJECT_MEMBER_PROPERTIES = "PROJECT_MEMBER_PROPERTIES";
    public static final String COMPANY_MEMBER_PROPERTIES = "COMPANY_MEMBER_PROPERTIES";
    public static final String COMPANY_PROPERTIES = "COMPANY_PROPERTIES";
    public static final String ROLE_PROPERTIES = "ROLE_PROPERTIES";
    public static final String SURVEY_REPORT_PROPERTIES = "SURVEY_REPORT_PROPERTIES";*/
    private static final int[] SQL_TYPES = new int[]{Types.LONGVARCHAR};
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final TypeReference LIST_TYPE_REF = new TypeReference<List<?>>() {
    };
    private static final TypeReference SET_TYPE_REF = new TypeReference<Set<?>>() {
    };


    private JavaType valueType = null;
    private Class<?> classType = null;

    @Override
    public int[] sqlTypes() {
        return SQL_TYPES;
    }

    @Override
    public Class<?> returnedClass() {
        return classType;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        /*if (x instanceof Chart) {
            try {
                return objectMapper.writeValueAsString(x).equals(objectMapper.writeValueAsString(y));
            } catch (JsonProcessingException e) {
                return false;
            }
        }*/
        return Objects.equals(x, y);
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        //// TODO:  add complete hashcode to the class
        return Objects.hashCode(x);
    }

//    @Override
//    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
//        return null;
//    }
//
//    @Override
//    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
//
//    }


    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner)
            throws HibernateException, SQLException {
        return nullSafeGet(rs, names, owner);
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session)
            throws HibernateException, SQLException {
        nullSafeSet(st, value, index);
    }

//    @Override
//    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
//        return nullSafeGet(rs, names, owner);
//    }
//
//    @Override
//    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
//        nullSafeSet(st, value, index);
//    }

    public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException {
        String value = rs.getString(names[0]);
        Object result = null;
        if (valueType == null) {
            throw new HibernateException("Value type not set.");
        }
        if (value != null && !value.equals("")) {
            try {
                result = objectMapper.readValue(value, valueType);
            } catch (IOException e) {
                throw new HibernateException("Exception deserializing value " + value, e);
            }
        }
        return result;
    }


    public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException {
        StringWriter sw = new StringWriter();
        if (value == null) {
            st.setNull(index, Types.VARCHAR);
        } else {
            try {
                objectMapper.writeValue(sw, value);
                st.setString(index, sw.toString());
            } catch (IOException e) {
                throw new HibernateException("Exception serializing value " + value, e);
            }
        }
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        //todo add copy constructor here
        if (value == null) {
            return null;
        } else if (valueType.isCollectionLikeType()) {
            try {
                Object newValue = value.getClass().newInstance();
                Collection newValueCollection = (Collection) newValue;
                /*Collection<?> collect = ((Collection<?>) value).stream().map(e -> {
                            if (e instanceof PublicClonable) {
                                return ((PublicClonable) e).clone();
                            }
                            return e;
                        }
                ).collect(Collectors.toList());
                newValueCollection.addAll(collect);*/
                return newValueCollection;
            } catch (InstantiationException | IllegalAccessException e) {
                throw new HibernateException("Failed to deep copy the collection-like value object.", e);
            }
        }
        /*else if (valueType.isTypeOrSubTypeOf(InvoiceContent.class)) {
            return new InvoiceContent((InvoiceContent) value);
        } else if (valueType.isTypeOrSubTypeOf(PublicProperties.class)) {
            return new PublicProperties((PublicProperties) value);
        } else if (valueType.isTypeOrSubTypeOf(DocumentProperties.class)) {
            return new DocumentProperties((DocumentProperties) value);
        } else if (valueType.isTypeOrSubTypeOf(AddressProperties.class)) {
            return ((AddressProperties) value).duplicate();
        } else if (valueType.isTypeOrSubTypeOf(ProviderItemProperties.class)) {
            if (value instanceof ProviderFolder.ProviderFolderProperties) {
                return new ProviderFolder.ProviderFolderProperties((ProviderFolder.ProviderFolderProperties) value);
            }
            if (value instanceof ProviderDocumentProperties) {
                return new ProviderDocumentProperties((ProviderDocumentProperties) value);
            }
            if (value instanceof ProviderSurveyProperties) {
                return new ProviderSurveyProperties((ProviderSurveyProperties) value);
            }
            if (value.getClass().equals(ProviderItemProperties.class)) {
                return new ProviderItemProperties((ProviderItemProperties) value);
            }
            if (value.getClass().equals(ProviderChart.ProviderChartProperties.class)) {
                return new ProviderChart.ProviderChartProperties((ProviderChart.ProviderChartProperties) value);
            }
            if (value.getClass().equals(ProviderSearchTagItem.ProviderSearchTagProperties.class)) {
                return new ProviderSearchTagItem.ProviderSearchTagProperties((ProviderSearchTagItem.ProviderSearchTagProperties) value);
            }
            if (value.getClass().equals(ProviderSurveyReportProperties.class)) {
                return new ProviderSurveyReportProperties((ProviderSurveyReportProperties) value);
            }

            throw new InvalidArgumentException("undefined ProviderItemProperties in JSONType file." +
                    " define copy constructor of " + value.getClass() + " and call it in " + this.getClass());

            //-----------------------
            // important

            // add equals and hash code to newly created Types!

            //-----------------------
        } else if (valueType.isTypeOrSubTypeOf(SurveyProperties.class)) {
            return ((SurveyProperties) value).duplicate();
        } else if (valueType.isTypeOrSubTypeOf(TemplateProperties.class)) {
            return ((TemplateProperties) value).deepCopy();
        } else if (valueType.isTypeOrSubTypeOf(ProviderTemplateProperties.class)) {
            return ((ProviderTemplateProperties) value).duplicate();
        } else if (valueType.isTypeOrSubTypeOf(CompanyTemplateProperties.class)) {
            return ((CompanyTemplateProperties) value).duplicate();
        } else if (valueType.isTypeOrSubTypeOf(TaskProperties.class)) {
            return ((TaskProperties) value).duplicate();
        } else if (valueType.isTypeOrSubTypeOf(SurveyResponseView.class)) {
            return ((SurveyResponseView) value).duplicate();
        } else if (valueType.isTypeOrSubTypeOf(Project.Properties.class)) {
            return new Project.Properties((Project.Properties) value);
        } else if (valueType.isTypeOrSubTypeOf(ProjectMember.Properties.class)) {
            return new ProjectMember.Properties((ProjectMember.Properties) value);
        } else if (valueType.isTypeOrSubTypeOf(CompanyMember.Properties.class)) {
            return new CompanyMember.Properties((CompanyMember.Properties) value);
        } else if (valueType.isTypeOrSubTypeOf(Company.Properties.class)) {
            return new Company.Properties((Company.Properties) value);
        } else if (valueType.isTypeOrSubTypeOf(me.telemart.man.domain.entity.task.task.role.Properties.class)) {
            return ((me.telemart.man.domain.entity.task.task.role.Properties) value).duplicate();
        } else if (valueType.isTypeOrSubTypeOf(AnswerProperties.class)) {
            return new AnswerProperties((AnswerProperties) value);
        } else if (valueType.isTypeOrSubTypeOf(QuestionProperties.class)) {
            if (value instanceof TextQuestion.TextQuestionProperties) {
                return new TextQuestion.TextQuestionProperties((TextQuestion.TextQuestionProperties) value);
            } else if (value instanceof MultipleChoiceQuestion.MultipleChoiceQuestionProperties) {
                if (value instanceof GridQuestionProperties) {
                    return new GridQuestionProperties((GridQuestionProperties) value);
                }
                return new MultipleChoiceQuestion.MultipleChoiceQuestionProperties
                        ((MultipleChoiceQuestion.MultipleChoiceQuestionProperties) value);
            } else if ((value instanceof IndicatorQuestionProperties)) {
                return new IndicatorQuestionProperties((IndicatorQuestionProperties) value);
            } else if ((value instanceof MultipleAnswerWithFieldsQuestionProperties)) {
                return new MultipleAnswerWithFieldsQuestionProperties((MultipleAnswerWithFieldsQuestionProperties) value);
            } else if ((value instanceof FileQuestionProperties)) {
                return new FileQuestionProperties((FileQuestionProperties) value);
            }
            throw new InvalidArgumentException("undefined SurveyQuestion in JSONType file." +
                    " define copy constructor of " + value.getClass() + " and call it in " + this.getClass());
        } else if (valueType.isTypeOrSubTypeOf(SurveyReportProperties.class)) {
            return ((SurveyReportProperties) value).copy();
        }*/
        else if (valueType.isTypeOrSubTypeOf(Map.class)) {
            try {
                Object newValue = value.getClass().newInstance();
                Map map = (Map) newValue;
                map.putAll((Map) value);
                return map;
            } catch (InstantiationException | IllegalAccessException e) {
                throw new HibernateException("Failed to deep copy the map-like value object.", e);
            }
        } else {
            try {
                return objectMapper.readValue(objectMapper.writeValueAsString(value), value.getClass());
            } catch (IOException e) {
                throw new HibernateException("HOSSEIN- no suitable clone constructor for valueType " + valueType.toString());
            }
        }
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) deepCopy(value);
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return deepCopy(cached);
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return deepCopy(original);
    }

    @Override
    public void setParameterValues(Properties parameters) {
        //todo add code here
        String type = parameters.getProperty("type");
        if (type.equals(LIST_TYPE)) {
            setElementValueType(parameters, ArrayList.class, LIST_TYPE_REF);
            classType = List.class;
        }
        if (type.equals(SET_TYPE)) {
            setElementValueType(parameters, HashSet.class, SET_TYPE_REF);
            classType = List.class;
        }
        /*if (type.equals(INVOICE_CONTENT)) {
            valueType = objectMapper.getTypeFactory().constructType(InvoiceContent.class);
            classType = InvoiceContent.class;
        }
        if (type.equals(PROVIDER_PUBLIC_PROPERTIES)) {
            valueType = objectMapper.getTypeFactory().constructType(PublicProperties.class);
            classType = PublicProperties.class;
        }
        if (type.equals(DOCUMENT_PROPERTIES)) {
            valueType = objectMapper.getTypeFactory().constructType(DocumentProperties.class);
            classType = DocumentProperties.class;
        }
        if (type.equals(PROVIDER_ITEM_PROPERTIES)) {
            valueType = objectMapper.getTypeFactory().constructType(ProviderItemProperties.class);
            classType = ProviderItemProperties.class;
        }
        if (type.equals(SURVEY_RESPONSE_VIEW)) {
            valueType = objectMapper.getTypeFactory().constructType(SurveyResponseView.class);
            classType = ProviderItemProperties.class;
        }
        if (type.equals(ADDRESS_PROPERTIES)) {
            valueType = objectMapper.getTypeFactory().constructType(AddressProperties.class);
            classType = AddressProperties.class;
        }
        if (type.equals(SURVEY_PROPERTIES)) {
            valueType = objectMapper.getTypeFactory().constructType(SurveyProperties.class);
            classType = SurveyProperties.class;
        }
        if (type.equals(SURVEY_QUESTION_PROPERTIES)) {
            valueType = objectMapper.getTypeFactory().constructType(QuestionProperties.class);
            classType = QuestionProperties.class;
        }
        if (type.equals(SURVEY_ANSWER_PROPERTIES)) {
            valueType = objectMapper.getTypeFactory().constructType(AnswerProperties.class);
            classType = AnswerProperties.class;
        }
        if (type.equals(PROVIDER_CHART)) {
            valueType = objectMapper.getTypeFactory().constructType(Chart.class);
            classType = Chart.class;
        }
        if (type.equals(TEMPLATE_PROPERTIES)) {
            valueType = objectMapper.getTypeFactory().constructType(TemplateProperties.class);
            classType = TemplateProperties.class;
        }
        if (type.equals(PROVIDER_TEMPLATE_PROPERTIES)) {
            valueType = objectMapper.getTypeFactory().constructType(ProviderTemplateProperties.class);
            classType = TemplateProperties.class;
        }
        if (type.equals(COMPANY_TEMPLATE_PROPERTIES)) {
            valueType = objectMapper.getTypeFactory().constructType(CompanyTemplateProperties.class);
            classType = TemplateProperties.class;
        }
        if (type.equals(TASK_PROPERTIES)) {
            valueType = objectMapper.getTypeFactory().constructType(TaskProperties.class);
            classType = TaskProperties.class;
        }
        if (type.equals(PROJECT_MEMBER_PROPERTIES)) {
            valueType = objectMapper.getTypeFactory().constructType(ProjectMember.Properties.class);
            classType = ProjectMember.Properties.class;
        }
        if (type.equals(COMPANY_MEMBER_PROPERTIES)) {
            valueType = objectMapper.getTypeFactory().constructType(CompanyMember.Properties.class);
            classType = CompanyMember.Properties.class;
        }
        if (type.equals(PROJECT_PROPERTIES)) {
            valueType = objectMapper.getTypeFactory().constructType(Project.Properties.class);
            classType = Project.Properties.class;
        }
        if (type.equals(COMPANY_PROPERTIES)) {
            valueType = objectMapper.getTypeFactory().constructType(Company.Properties.class);
            classType = Company.Properties.class;
        }
        if (type.equals(ROLE_PROPERTIES)) {
            valueType = objectMapper.getTypeFactory().constructType(me.telemart.man.domain.entity.task.task.role.Properties.class);
            classType = me.telemart.man.domain.entity.task.task.role.Properties.class;
        }
        if (type.equalsIgnoreCase(SURVEY_REPORT_PROPERTIES)) {
            valueType = objectMapper.getTypeFactory().constructType(SurveyReportProperties.class);
            classType = SurveyReportProperties.class;
        }*/
        if (type.equals(MAP_TYPE)) {
            String keyTypeStr = parameters.getProperty("key");
            String valueTypeStr = parameters.getProperty("value");
            if (keyTypeStr != null && valueTypeStr != null) {
                try {
                    valueType = objectMapper.getTypeFactory().constructMapType(HashMap.class,
                            Class.forName(keyTypeStr), Class.forName(valueTypeStr));
                } catch (ClassNotFoundException e) {
                    throw new IllegalArgumentException("Type " + parameters.getProperty("element") + " is not a valid type.");
                }
            } else {
                valueType = objectMapper.getTypeFactory().constructMapType(HashMap.class,
                        objectMapper.getTypeFactory().unknownType(), objectMapper.getTypeFactory().unknownType());
            }
            classType = Map.class;
        }
    }

    private void setElementValueType(Properties parameters, Class<? extends Collection> collectionClass, TypeReference GenericTypeReference) {
        if (parameters.getProperty("element") != null) {
            try {
                valueType = objectMapper.getTypeFactory().constructCollectionType(collectionClass,
                        Class.forName(parameters.getProperty("element")));
            } catch (ClassNotFoundException e) {
                throw new IllegalArgumentException("Type " + parameters.getProperty("element") + " is not a valid type.");
            }
        } else {
            valueType = objectMapper.getTypeFactory().constructType(GenericTypeReference);
        }
    }
}